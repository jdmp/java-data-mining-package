package org.jdmp.gui.variable.actions;

import javax.swing.JComponent;

import org.jdmp.core.variable.Variable;
import org.jdmp.gui.actions.ObjectAction;

public abstract class VariableAction extends ObjectAction {

	public VariableAction(JComponent c, Variable v) {
		super(c, v);
	}

	public Variable getVariable() {
		return (Variable) getObject();
	}
}
