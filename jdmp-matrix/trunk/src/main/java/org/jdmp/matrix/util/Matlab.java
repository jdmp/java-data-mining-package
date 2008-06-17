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
import org.jdmp.matrix.calculation.Calculation.Ret;

public class Matlab {

	public static final String[] SEARCH = new String[] { "/usr/bin/matlab",
			"/opt/matlab/bin/matlab" };

	public static final String MATLABPARAMETERS = "-nosplash -nojvm";

	private static String pathToMatlab = null;

	private BufferedReader input = null;

	private BufferedWriter output = null;

	private BufferedReader error = null;

	private Process matlabProcess = null;

	private boolean running = false;

	private static Matlab matlab = null;

	public static synchronized Matlab getInstance() throws Exception {
		if (matlab == null) {
			matlab = getInstance(findMatlab());
		}
		return matlab;
	}

	private static String findMatlab() {
		if (pathToMatlab == null) {
			File file = new File(System.getProperty("user.home") + "/matlab/bin/matlab");
			if (file.exists()) {
				pathToMatlab = file.getAbsolutePath();
				return pathToMatlab;
			}
			for (String s : SEARCH) {
				file = new File(s);
				if (file.exists()) {
					pathToMatlab = file.getAbsolutePath();
					return pathToMatlab;
				}
			}
		}
		return pathToMatlab;
	}

	public static synchronized Matlab getInstance(String pathToMatlab) throws Exception {
		if (matlab == null) {
			matlab = new Matlab(pathToMatlab);
		}
		return matlab;
	}

	private Matlab(String pathToMatlab) throws Exception {
		matlabProcess = Runtime.getRuntime().exec(pathToMatlab + " " + MATLABPARAMETERS);
		output = new BufferedWriter(new OutputStreamWriter(matlabProcess.getOutputStream()));
		input = new BufferedReader(new InputStreamReader(matlabProcess.getInputStream()));
		error = new BufferedReader(new InputStreamReader(matlabProcess.getErrorStream()));
		String startMessage = getFromMatlab();
		if (startMessage != null && startMessage.length() > 0) {
			running = true;
			return;
		}
		throw new Exception("could not start Matlab");
	}

	private synchronized String getFromMatlab() throws Exception {
		boolean endSeen = false;
		StringBuffer sb = new StringBuffer();

		while (true) {
			while (!input.ready()) {
				Thread.yield();
			}
			while (input.ready()) {

				char c = (char) input.read();
				sb.append((char) c);

				if (c == '>') {
					if (endSeen) {
						return sb.substring(0, sb.length() - 2);
					}
					endSeen = true;
				} else {
					endSeen = false;
				}
			}
		}
	}

	public String execute(String command) throws Exception {
		sendToMatlab(command);
		return getFromMatlab();
	}

	public synchronized void shutdown() throws Exception {
		sendToMatlab("exit");
		matlabProcess.waitFor();
		output.close();
		input.close();
	}

	private synchronized void sendToMatlab(String command) throws Exception {
		if (!command.endsWith("\n")) {
			command = command + "\n";
		}
		output.write(command, 0, command.length());
		output.flush();
	}

	public void setMatrix(String label, Matrix matrix) throws Exception {
		execute(label + "=" + matrix.exportToString(Format.M));
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
		return findMatlab() != null;
	}

	public void plot(Matrix matrix, String... format) throws Exception {
		setMatrix("jdmpmatrix", matrix);
		execute("figure;");
		execute("plot(jdmpmatrix" + toString(format) + ");");
	}

	public void hist(Matrix matrix, String... format) throws Exception {
		setMatrix("jdmpmatrix", matrix);
		execute("figure;");
		execute("hist(jdmpmatrix" + toString(format) + ");");
	}

	public void surf(Matrix matrix, String... format) throws Exception {
		setMatrix("jdmpmatrix", matrix);
		execute("figure;");
		execute("surf(jdmpmatrix" + toString(format) + ");");
	}

	public void imagesc(Matrix matrix, String... format) throws Exception {
		setMatrix("jdmpmatrix", matrix);
		execute("figure;");
		execute("imagesc(jdmpmatrix" + toString(format) + ");");
	}

	public void bar(Matrix matrix, String... format) throws Exception {
		setMatrix("jdmpmatrix", matrix);
		execute("figure;");
		execute("bar(jdmpmatrix" + toString(format) + ");");
	}

	public void barh(Matrix matrix, String... format) throws Exception {
		setMatrix("jdmpmatrix", matrix);
		execute("figure;");
		execute("barh(jdmpmatrix" + toString(format) + ");");
	}

	public void stem(Matrix matrix, String... format) throws Exception {
		setMatrix("jdmpmatrix", matrix);
		execute("figure;");
		execute("stem(jdmpmatrix" + toString(format) + ");");
	}

	public void pie(Matrix matrix, String... format) throws Exception {
		setMatrix("jdmpmatrix", matrix);
		execute("figure;");
		execute("pie(jdmpmatrix" + toString(format) + ");");
	}

	public void pie3(Matrix matrix, String... format) throws Exception {
		setMatrix("jdmpmatrix", matrix);
		execute("figure;");
		execute("pie3(jdmpmatrix" + toString(format) + ");");
	}

	public void pairs(Matrix matrix, String... format) throws Exception {
		execute("figure;");
		long cols = matrix.getColumnCount();
		for (int r = 0; r < cols; r++) {
			for (int c = 0; c < cols; c++) {
				long i = r * cols + c;
				Matrix x = matrix.selectColumns(Ret.NEW, r);
				Matrix y = matrix.selectColumns(Ret.NEW, c);
				execute("subplot(" + cols + "," + cols + "," + (i + 1) + ");");
				setMatrix("jdmpmatrix_x", x);
				setMatrix("jdmpmatrix_y", y);
				execute("plot(jdmpmatrix_x,jdmpmatrix_y" + toString(format) + ");");
			}
		}
	}

	public void plot(Matrix x, Matrix y, String... format) throws Exception {
		setMatrix("jdmpmatrix_x", x);
		setMatrix("jdmpmatrix_y", y);
		execute("figure;");
		execute("plot(jdmpmatrix_x,jdmpmatrix_y" + toString(format) + ");");
	}

	public static String toString(String[] strings) {
		if (strings.length != 0) {
			return ",'" + strings[0] + "'";
		} else {
			return "";
		}
	}
}