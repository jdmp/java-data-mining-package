package org.jdmp.gui.variable;

import org.jdmp.core.variable.Variable;
import org.jdmp.gui.AbstractGUIObject;

public class VariableGUIObject extends AbstractGUIObject {
	private static final long serialVersionUID = 9145766876402222560L;

	private Variable variable = null;

	public VariableGUIObject(Variable v) {
		this.variable = v;
	}

	@Override
	public void clear() {
		variable.clear();
	}

	public String getLongStatus() {
		return variable.getLongStatus();
	}

	public String getShortStatus() {
		return variable.getShortStatus();
	}

	public void dispose() {
		variable.dispose();
	}

	public Variable getVariable() {
		return variable;
	}

}
