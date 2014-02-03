/*
 * Copyright (C) 2008-2014 by Holger Arndt
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

package org.jdmp.bsh;

import javax.swing.JFrame;

import bsh.EvalError;
import bsh.Interpreter;
import bsh.util.JConsole;

public class BshInterpreter {

	private Interpreter interpreter = null;

	public BshInterpreter() {
		interpreter = new Interpreter();
		try {
			interpreter.eval("import org.ujmp.*");
			interpreter.eval("import org.jdmp.*");
		} catch (EvalError e) {
			e.printStackTrace();
		}
	}

	public void set(String name, Object object) {
		try {
			interpreter.set(name, object);
		} catch (EvalError e) {
			e.printStackTrace();
		}
	}

	public Object eval(String command) {
		try {
			return interpreter.eval(command);
		} catch (EvalError e) {
			e.printStackTrace();
			return null;
		}
	}

	public Object get(String name) {
		try {
			return interpreter.get(name);
		} catch (EvalError e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) throws Exception {
		JFrame f = new JFrame();
		JConsole console = new JConsole();
		f.setContentPane(console);
		Interpreter interpreter = new Interpreter(console);
		interpreter.eval("import org.ujmp.core.*;");
		interpreter.eval("Matrix m=MatrixFactory.eye(new long[]{10,10});");
		new Thread(interpreter).start(); // start a thread to call the run()
		// method

		f.setVisible(true);

	}

}
