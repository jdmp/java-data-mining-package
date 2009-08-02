package org.jdmp.gui.variable;

import org.jdmp.core.variable.Variable;
import org.jdmp.gui.matrix.MatrixIcon;
import org.jdmp.matrix.Matrix;

public class VariableIcon extends MatrixIcon {
	private static final long serialVersionUID = 67250050019574827L;

	private Variable variable = null;

	public VariableIcon(Variable variable) {
		super(variable.getMatrix());
		this.variable = variable;
	}

	public Matrix getMatrix() {
		return variable.getMatrix();
	}

	public Variable getVariable() {
		return variable;
	}

	public void setVariable(Variable variable) {
		this.variable = variable;
	}

}
