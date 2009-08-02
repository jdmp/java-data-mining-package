package org.jdmp.gui.variable;

import org.ujmp.core.Matrix;
import org.ujmp.gui.matrix.MatrixIcon;

public class VariableIcon extends MatrixIcon {
	private static final long serialVersionUID = 67250050019574827L;

	private VariableGUIObject variable = null;

	public VariableIcon(VariableGUIObject variable) {
		super(variable.getVariable().getMatrix());
		this.variable = variable;
	}

	@Override
	public Matrix getMatrix() {
		return variable.getVariable().getMatrix();
	}

	public VariableGUIObject getVariable() {
		return variable;
	}

	public void setVariable(VariableGUIObject variable) {
		this.variable = variable;
	}

}
