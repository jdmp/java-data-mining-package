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
import org.jdmp.matrix.Matrix.Format;

public class R {

	public static final String[] SEARCH = new String[] { "/usr/bin/R", "/opt/R/bin/R" };

	public static final int POLLINTERVAL = 100;

	public static final int MAXPOLLS = 10;

	private BufferedReader input = null;

	private BufferedWriter output = null;

	private BufferedReader error = null;

	private Process rProcess = null;

	private boolean running = false;

	private static R r = null;

	private static String pathToR = null;

	public static synchronized R getInstance() throws Exception {
		if (r == null) {
			r = getInstance(findR());
		}
		return r;
	}

	public static synchronized R getInstance(String pathToOctave) throws Exception {
		if (r == null) {
			r = new R(pathToR);
		}
		return r;
	}

	private R(String pathToR) throws Exception {
		rProcess = Runtime.getRuntime().exec(pathToR + " --no-save --no-readline");
		output = new BufferedWriter(new OutputStreamWriter(rProcess.getOutputStream()));
		input = new BufferedReader(new InputStreamReader(rProcess.getInputStream()));
		error = new BufferedReader(new InputStreamReader(rProcess.getErrorStream()));
		String startMessage = getFromR();
		if (startMessage != null && startMessage.length() > 0) {
			running = true;
			return;
		}
		throw new Exception("could not start R");
	}

	private synchronized String getFromR() throws Exception {
		boolean lfSeen = false;
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

			if (lfSeen) {
				if (c == '>') {
					return sb.toString();
				} else {
					lfSeen = false;
				}
			}
			if (c == '\n') {
				lfSeen = true;
			}

		}
		return sb.toString();
	}

	public String execute(String command) throws Exception {
		sendToR(command);
		String cur = "";
		String last = "";

		cur = getFromR();

		while (cur != null && cur.length() > 0) {
			last = cur;
			cur = getFromR();
		}

		return last;
	}

	public synchronized void shutdown() throws Exception {
		sendToR("q()");
		rProcess.waitFor();
		output.close();
		input.close();
	}

	private synchronized void sendToR(String command) throws Exception {
		if (!command.endsWith("\n")) {
			command += "\n";
		}
		output.write(command, 0, command.length());
		output.flush();
	}

	public void setMatrix(String label, Matrix matrix) throws Exception {
		execute(label + " <- " + matrix.exportToString(Format.R));
	}

	private static String findR() {
		File file = null;
		if (pathToR == null) {
			for (String s : SEARCH) {
				file = new File(s);
				if (file.exists()) {
					pathToR = file.getAbsolutePath();
					return pathToR;
				}
			}
		}
		return pathToR;
	}

	// TODO: change this method, because it does not work for matrices with many
	// columns
	public Matrix getMatrix(String label) throws Exception {
		try {
			Matrix matrix = null;
			String rawText = execute(label);

			String[] rawValues = rawText.split("\n");

			int rows = rawValues.length - 2;
			for (int r = 2; r < rawValues.length; r++) {
				String[] cols = rawValues[r].split("[\\s]+");
				int c = cols.length;
			}

			return matrix;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean isAvailable() {
		return findR() != null;
	}

	public void plot(Matrix matrix) throws Exception {
		setMatrix("jdmpmatrix", matrix);
		execute("plot(jdmpmatrix);");
		execute("plot(jdmpmatrix);");
	}

}
