package org.jdmp.gui.module.actions;

import javax.swing.JComponent;

import org.jdmp.gui.actions.ObjectAction;
import org.jdmp.gui.module.ModuleGUIObject;

public abstract class ModuleAction extends ObjectAction {

	public ModuleAction(JComponent c, ModuleGUIObject module) {
		super(c, module);
	}

	public ModuleGUIObject getModule() {
		return (ModuleGUIObject) getObject();
	}

	public void setModule(ModuleGUIObject i) {
		setObject(i);
	}

}
