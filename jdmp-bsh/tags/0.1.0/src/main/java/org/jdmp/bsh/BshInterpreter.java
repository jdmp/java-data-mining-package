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
