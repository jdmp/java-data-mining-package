package org.jdmp.gui.module.actions;

import javax.swing.JComponent;

import org.jdmp.core.module.HasModules;
import org.jdmp.gui.actions.ObjectAction;
import org.jdmp.matrix.interfaces.GUIObject;

public abstract class ModuleListAction extends ObjectAction {

	public ModuleListAction(JComponent c, HasModules iModules) {
		super(c, (GUIObject) iModules);
	}

	public HasModules getIModules() {
		return (HasModules) getObject();
	}

}
