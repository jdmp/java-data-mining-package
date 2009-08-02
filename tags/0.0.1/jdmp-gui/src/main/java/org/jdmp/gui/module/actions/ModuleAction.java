package org.jdmp.gui.module.actions;

import javax.swing.JComponent;

import org.jdmp.core.module.Module;
import org.jdmp.gui.actions.ObjectAction;

public abstract class ModuleAction extends ObjectAction {

	public ModuleAction(JComponent c, Module module) {
		super(c, module);
	}

	public Module getModule() {
		return (Module) getObject();
	}

	public void setModule(Module i) {
		setObject(i);
	}

}
