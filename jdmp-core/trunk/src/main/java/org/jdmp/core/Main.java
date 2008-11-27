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

package org.jdmp.core;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintStream;

import org.jdmp.core.interpreter.Result;
import org.jdmp.core.module.Module;
import org.jdmp.core.module.ModuleFactory;

public class Main {

	protected static final PrintStream out = System.out;

	protected static String script = null;
	protected static String file = null;
	protected static boolean textMode = true;

	public static void main(String[] args) throws Exception {
		evaluateParameters(args);
		run();
	}

	protected static void evaluateParameters(String[] args) {
		// parse command line

		try {

			for (int i = 0; i < args.length; i++) {

				if (args[i].equals("-e") || args[i].equals("--execute")) {
					script = args[++i];
				} else if (args[i].equals("-f") || args[i].equals("--file")) {
					file = args[++i];
				} else if (args[i].equals("-g") || args[i].equals("--gui")) {
					textMode = false;
				} else if (args[i].equals("-t") || args[i].equals("--text")) {
					textMode = true;
				} else if (args[i].equals("-h") || args[i].equals("--help")) {
					help();
					System.exit(0);
				}

			}

		} catch (Exception e) {
			help();
			System.exit(1);
		}
	}

	private static void help() {
		out.println();
		out.println("JDMP supports the following command line options:");
		out.println("    JDMP [-tgh] [-e command] [-f file]");
		out.println();
		out.println("    -e, --execute     executes a command at startup");
		out.println("    -f, --file        executes the commands in the specifiedfile");
		out.println("    -t, --text        starts JDMP in textmode");
		out.println("    -g, --gui         starts JDMP in graphics mode");
		out.println("    -h, --help        displays this help text");
		out.println();
		out.println();
	}

	public static void run() throws Exception {
		out.println("/*");
		out.println(" *       Welcome to the Java Data Mining Package (JDMP)");
		out.println(" * Copyright (C) 2008 by Holger Arndt, A. Naegele and M. Bundschus");
		out.println(" *");
		out.println(" * JDMP is free software; you can redistribute it and/or modify");
		out.println(" * it under the terms of the GNU Lesser General Public License as");
		out.println(" * published by the Free Software Foundation; either version 2");
		out.println(" * of the License, or (at your option) any later version.");
		out.println(" *");
		out.println(" * JDMP is distributed in the hope that it will be useful,");
		out.println(" * but WITHOUT ANY WARRANTY; without even the implied warranty of");
		out.println(" * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the");
		out.println(" * GNU Lesser General Public License for more details.");
		out.println(" *");
		out.println(" * If you would like to find out more, please take a look at the");
		out.println(" * homepage of JDMP at http://www.jdmp.org/");
		out.println(" */");
		out.println();
		out.println();

		Module module = ModuleFactory.emptyModule();

		if (textMode == false) {
			try {
				Class.forName("org.jdmp.gui.JDMP");
				module.showGUI();
			} catch (Exception e) {
				out.println("Graphics mode is not available, probably because jdmp-gui is not");
				out.println("in the class path.");
				out.println();
			}
		}

		// read from standard input

		LineNumberReader lr = new LineNumberReader(new InputStreamReader(System.in));

		while (true) {

			try {

				out.print(">> ");

				String line = lr.readLine();

				if (line.startsWith(">> ")) {
					line = line.substring(3);
				}

				if (!line.endsWith(";")) {
					Result result = module.execute(line + ";");
					out.println(result);
				} else {
					module.execute(line);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}
