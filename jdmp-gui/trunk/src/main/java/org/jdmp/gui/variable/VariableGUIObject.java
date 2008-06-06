package org.jdmp.gui.variable;

import org.jdmp.core.util.AbstractGUIObject;
import org.jdmp.core.variable.Variable;

public class VariableGUIObject extends AbstractGUIObject {
	private static final long serialVersionUID = 9145766876402222560L;

	private Variable variable = null;

	public VariableGUIObject(Variable v) {
		this.variable = v;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	public String getLongStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getShortStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	public void dispose() {
		// TODO Auto-generated method stub
	}

	public Variable getVariable() {
		return variable;
	}

}
