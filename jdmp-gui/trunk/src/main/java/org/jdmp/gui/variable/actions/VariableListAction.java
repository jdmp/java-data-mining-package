package org.jdmp.gui.variable.actions;

import javax.swing.JComponent;

import org.jdmp.core.util.AbstractGUIObject;
import org.jdmp.core.variable.HasVariables;
import org.jdmp.gui.actions.ObjectAction;

public abstract class VariableListAction extends ObjectAction {

	public VariableListAction(JComponent c, HasVariables iVariables) {
		super(c, (AbstractGUIObject) iVariables);
	}

	public HasVariables getIVariables() {
		return (HasVariables) getObject();
	}

}
