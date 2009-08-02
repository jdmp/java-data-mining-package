package org.jdmp.gui.variable.actions;

import javax.swing.JComponent;

import org.jdmp.core.variable.HasVariables;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.gui.actions.ObjectAction;

public abstract class VariableListAction extends ObjectAction {

	public VariableListAction(JComponent c, HasVariables iVariables) {
		super(c, (GUIObject) iVariables);
	}

	public HasVariables getIVariables() {
		return (HasVariables) getObject();
	}

}
