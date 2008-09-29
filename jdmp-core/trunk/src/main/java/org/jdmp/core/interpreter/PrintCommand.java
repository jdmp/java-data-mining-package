package org.jdmp.core.interpreter;

public class PrintCommand extends Command {

	private String variable = null;

	public PrintCommand() {
	}

	public PrintCommand(String variable) {
		this.variable = variable;
	}

	public String toString() {
		return variable;
	}

	public String getVariable() {
		return variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}

}
