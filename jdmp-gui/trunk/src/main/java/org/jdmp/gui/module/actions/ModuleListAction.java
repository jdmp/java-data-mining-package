package org.jdmp.gui.module.actions;

import javax.swing.JComponent;

import org.jdmp.core.module.HasModuleList;
import org.jdmp.gui.actions.ObjectAction;
import org.ujmp.core.interfaces.GUIObject;

public abstract class ModuleListAction extends ObjectAction {

	public ModuleListAction(JComponent c, HasModuleList iModules) {
		super(c, (GUIObject) iModules);
	}

	public HasModuleList getIModules() {
		return (HasModuleList) getObject();
	}

}
