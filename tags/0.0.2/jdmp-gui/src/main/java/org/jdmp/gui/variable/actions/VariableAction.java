package org.jdmp.gui.variable.actions;

import javax.swing.JComponent;

import org.jdmp.gui.variable.VariableGUIObject;
import org.ujmp.gui.actions.ObjectAction;

public abstract class VariableAction extends ObjectAction {

	public VariableAction(JComponent c, VariableGUIObject v) {
		super(c, v);
	}

	public VariableGUIObject getVariable() {
		return (VariableGUIObject) getObject();
	}
}
