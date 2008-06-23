/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
 *
 * This file is part of the Java Data Mining Package (JDMP).
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership and licensing.
 *
 * JDMP is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * JDMP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with JDMP; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

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

	public static final String[] SEARCH = new String[] { System.getProperty("Octave"),
			"/usr/bin/octave", "/opt/octave/bin/octave" };

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
			File file = null;
			for (String s : SEARCH) {
				if (s != null) {
					file = new File(s);
					if (file.exists()) {
						pathToOctave = file.getAbsolutePath();
						return pathToOctave;
					}
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
					matrix.setAsDouble(Double.parseDouble(rawValues[i++]), r, c);
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

	public void plot(Matrix matrix, String... format) throws Exception {
		setMatrix("jdmpmatrix", matrix);
		execute("figure;");
		execute("plot(jdmpmatrix" + toString(format) + ");");
	}

	public void loglog(Matrix matrix, String... format) throws Exception {
		setMatrix("jdmpmatrix", matrix);
		execute("figure;");
		execute("loglog(jdmpmatrix" + toString(format) + ");");
	}

	public void semilogx(Matrix matrix, String... format) throws Exception {
		setMatrix("jdmpmatrix", matrix);
		execute("figure;");
		execute("semilogx(jdmpmatrix" + toString(format) + ");");
	}

	public void semilogy(Matrix matrix, String... format) throws Exception {
		setMatrix("jdmpmatrix", matrix);
		execute("figure;");
		execute("semilogy(jdmpmatrix" + toString(format) + ");");
	}

	public void bar(Matrix matrix, String... format) throws Exception {
		setMatrix("jdmpmatrix", matrix);
		execute("figure;");
		execute("bar(jdmpmatrix" + toString(format) + ");");
	}

	public void stairs(Matrix matrix, String... format) throws Exception {
		setMatrix("jdmpmatrix", matrix);
		execute("figure;");
		execute("stairs(jdmpmatrix" + toString(format) + ");");
	}

	public void hist(Matrix matrix, String... format) throws Exception {
		setMatrix("jdmpmatrix", matrix);
		execute("figure;");
		execute("hist(jdmpmatrix" + toString(format) + ");");
	}

	public void plot(Matrix x, Matrix y, String... format) throws Exception {
		setMatrix("jdmpmatrix_x", x);
		setMatrix("jdmpmatrix_y", y);
		execute("figure;");
		execute("plot(jdmpmatrix_x,jdmpmatrix_y" + toString(format) + ");");
	}

	public void loglog(Matrix x, Matrix y, String... format) throws Exception {
		setMatrix("jdmpmatrix_x", x);
		setMatrix("jdmpmatrix_y", y);
		execute("figure;");
		execute("loglog(jdmpmatrix_x,jdmpmatrix_y" + toString(format) + ");");
	}

	public void semilogx(Matrix x, Matrix y, String... format) throws Exception {
		setMatrix("jdmpmatrix_x", x);
		setMatrix("jdmpmatrix_y", y);
		execute("figure;");
		execute("semilogx(jdmpmatrix_x,jdmpmatrix_y" + toString(format) + ");");
	}

	public void semilogy(Matrix x, Matrix y, String... format) throws Exception {
		setMatrix("jdmpmatrix_x", x);
		setMatrix("jdmpmatrix_y", y);
		execute("figure;");
		execute("semilogy(jdmpmatrix_x,jdmpmatrix_y" + toString(format) + ");");
	}

	public void bar(Matrix x, Matrix y, String... format) throws Exception {
		setMatrix("jdmpmatrix_x", x);
		setMatrix("jdmpmatrix_y", y);
		execute("figure;");
		execute("bar(jdmpmatrix_x,jdmpmatrix_y" + toString(format) + ");");
	}

	public void stairs(Matrix x, Matrix y, String... format) throws Exception {
		setMatrix("jdmpmatrix_x", x);
		setMatrix("jdmpmatrix_y", y);
		execute("figure;");
		execute("stairs(jdmpmatrix_x,jdmpmatrix_y" + toString(format) + ");");
	}

	public void hist(Matrix x, Matrix y, String... format) throws Exception {
		setMatrix("jdmpmatrix_x", x);
		setMatrix("jdmpmatrix_y", y);
		execute("figure;");
		execute("hist(jdmpmatrix_x,jdmpmatrix_y" + toString(format) + ");");
	}

	public void polar(Matrix theta, Matrix rho, String... format) throws Exception {
		setMatrix("jdmpmatrix_theta", theta);
		setMatrix("jdmpmatrix_rho", rho);
		execute("figure;");
		execute("hist(jdmpmatrix_theta,jdmpmatrix_rho" + toString(format) + ");");
	}

	public void contour(Matrix z, Matrix n, Matrix x, Matrix y, String... format) throws Exception {
		setMatrix("jdmpmatrix_z", z);
		setMatrix("jdmpmatrix_n", n);
		setMatrix("jdmpmatrix_x", x);
		setMatrix("jdmpmatrix_y", y);
		execute("figure;");
		execute("contour(jdmpmatrix_z,jdmpmatrix_n,jdmpmatrix_x,jdmpmatrix_y" + toString(format)
				+ ");");
	}

	public void mesh(Matrix x, Matrix y, Matrix z, String... format) throws Exception {
		setMatrix("jdmpmatrix_x", x);
		setMatrix("jdmpmatrix_y", y);
		setMatrix("jdmpmatrix_z", z);
		execute("figure;");
		execute("mesh(jdmpmatrix_x,jdmpmatrix_y,jdmpmatrix_z" + toString(format) + ");");
	}

	public static String toString(String[] strings) {
		if (strings.length != 0) {
			return ",'" + strings[0] + "'";
		} else {
			return "";
		}
	}

}
