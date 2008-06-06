package org.jdmp.gui.variable.actions;

import javax.swing.JComponent;

import org.jdmp.gui.actions.ObjectAction;
import org.jdmp.gui.variable.VariableGUIObject;

public abstract class VariableAction extends ObjectAction {

	public VariableAction(JComponent c, VariableGUIObject v) {
		super(c, v);
	}

	public VariableGUIObject getVariable() {
		return (VariableGUIObject) getObject();
	}
}
