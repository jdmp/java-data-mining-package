package org.jdmp.core.interpreter;

import org.ujmp.core.Matrix;

public class VariableCommand extends Command {

	private String variable = null;

	private Matrix value = null;

	public void setVariable(String variable) {
		this.variable = variable;
	}

	public void setValue(Matrix value) {
		this.value = value;
	}

	public String toString() {
		return variable + " = " + value;
	}

	public Matrix getValue() {
		return value;
	}

	public String getVariable() {
		return variable;
	}

}
