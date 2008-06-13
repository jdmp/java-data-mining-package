package org.jdmp.gui.module;

import org.jdmp.core.module.Module;
import org.jdmp.gui.AbstractGUIObject;

public class ModuleGUIObject extends AbstractGUIObject {
	private static final long serialVersionUID = -317629566695423286L;

	private Module module = null;

	public ModuleGUIObject(Module module) {
		this.module = module;
	}

	public void clear() {
		module.clear();
	}

	public void dispose() {
		module.dispose();
	}

}
