package org.jdmp.matrix.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.Matrix.Format;

public class Octave {

	public static final String[] SEARCH = new String[] { "/usr/bin/octave",
			"/opt/octave/bin/octave" };

	public static final int POLLINTERVAL = 100;

	public static final int MAXPOLLS = 10;

	private BufferedReader input = null;

	private BufferedWriter output = null;

	private BufferedReader error = null;

	private Process octaveProcess = null;

	private boolean running = false;

	private static Octave octave = null;

	private static String pathToOctave = null;

	public static synchronized Octave getInstance() throws Exception {
		if (octave == null) {
			octave = getInstance(findOctave());
		}
		return octave;
	}

	public static synchronized Octave getInstance(String pathToOctave) throws Exception {
		if (octave == null) {
			octave = new Octave(pathToOctave);
		}
		return octave;
	}

	private Octave(String pathToOctave) throws Exception {
		octaveProcess = Runtime.getRuntime().exec(pathToOctave);
		output = new BufferedWriter(new OutputStreamWriter(octaveProcess.getOutputStream()));
		input = new BufferedReader(new InputStreamReader(octaveProcess.getInputStream()));
		error = new BufferedReader(new InputStreamReader(octaveProcess.getErrorStream()));
		String startMessage = getFromOctave();
		if (startMessage != null && startMessage.length() > 0) {
			running = true;
			return;
		}
		throw new Exception("could not start Octave");
	}

	private synchronized String getFromOctave() throws Exception {
		boolean colonSeen = false;
		boolean numberSeen = false;
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < MAXPOLLS; i++) {
			if (!input.ready()) {
				Thread.sleep(POLLINTERVAL);
			} else {
				break;
			}
		}

		while (input.ready()) {

			char c = (char) input.read();
			sb.append((char) c);

			if (numberSeen) {
				if (c == '>') {
					return sb.toString();
				} else {
					colonSeen = false;
					numberSeen = false;
				}
			} else if (colonSeen) {
				if (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5'
						|| c == '6' || c == '7' || c == '8' || c == '9') {
					numberSeen = true;
				} else {
					colonSeen = false;
					numberSeen = false;
				}
			} else {
				if (c == ':') {
					colonSeen = true;
				}
			}
		}
		return sb.toString();
	}

	public String execute(String command) throws Exception {
		sendToOctave(command);
		return getFromOctave();
	}

	public synchronized void shutdown() throws Exception {
		sendToOctave("exit");
		octaveProcess.waitFor();
		output.close();
		input.close();
	}

	private synchronized void sendToOctave(String command) throws Exception {
		if (!command.endsWith("\n")) {
			command = command + "\n";
		}
		output.write(command, 0, command.length());
		output.flush();
	}

	public void setMatrix(String label, Matrix matrix) throws Exception {
		execute(label + "=" + matrix.exportToString(Format.M));
	}

	private static String findOctave() {
		if (pathToOctave == null) {
			File file = new File(System.getProperty("user.home") + "/octave/bin/octave");
			if (file.exists()) {
				pathToOctave = file.getAbsolutePath();
				return pathToOctave;
			}
			for (String s : SEARCH) {
				file = new File(s);
				if (file.exists()) {
					pathToOctave = file.getAbsolutePath();
					return pathToOctave;
				}
			}
		}
		return pathToOctave;
	}

	public Matrix getMatrix(String label) throws Exception {
		try {
			String rawRows = execute("fprintf(1,'%d\\n',size(" + label + ",1));");
			int rows = Integer.parseInt(rawRows.trim());
			String rawCols = execute("fprintf(1,'%d\\n',size(" + label + ",2));");
			int cols = Integer.parseInt(rawCols.trim());

			String rawText = execute("fprintf(1,'%55.55f\\n'," + label + ")");
			String[] rawValues = rawText.split("\n");

			Matrix matrix = MatrixFactory.zeros(rows, cols);

			int i = 0;
			for (int c = 0; c < cols; c++) {
				for (int r = 0; r < rows; r++) {
					matrix.setDouble(Double.parseDouble(rawValues[i++]), r, c);
				}
			}

			return matrix;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean isAvailable() {
		return findOctave() != null;
	}
}
