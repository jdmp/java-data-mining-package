package org.jdmp.gui.module.actions;

import javax.swing.JComponent;

import org.jdmp.core.module.HasModules;
import org.jdmp.core.util.AbstractGUIObject;
import org.jdmp.gui.actions.ObjectAction;

public abstract class ModuleListAction extends ObjectAction {

	public ModuleListAction(JComponent c, HasModules iModules) {
		super(c, (AbstractGUIObject) iModules);
	}

	public HasModules getIModules() {
		return (HasModules) getObject();
	}

}
