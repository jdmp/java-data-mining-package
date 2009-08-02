package org.jdmp.gui.module.actions;

import javax.swing.JComponent;

import org.jdmp.core.module.HasModuleList;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.gui.actions.ObjectAction;

public abstract class ModuleListAction extends ObjectAction {

	public ModuleListAction(JComponent c, HasModuleList iModules) {
		super(c, (GUIObject) iModules);
	}

	public HasModuleList getIModules() {
		return (HasModuleList) getObject();
	}

}
