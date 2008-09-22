package org.jdmp.gui.module;

import javax.swing.Icon;

import org.jdmp.core.module.Module;
import org.ujmp.gui.AbstractGUIObject;

public class ModuleGUIObject extends AbstractGUIObject {
	private static final long serialVersionUID = -317629566695423286L;

	private Module module = null;

	public ModuleGUIObject(Module module) {
		this.module = module;
	}

	public void clear() {
		module.clear();
	}

	public Icon getIcon() {
		return null;
	}

	public String getLabel() {
		return module.getLabel();
	}

	public void setLabel(String label) {
		module.setLabel(label);
	}

	public String getDescription() {
		return module.getDescription();
	}

	public void setDescription(String description) {
		module.setDescription(description);
	}

	@Override
	public String toString() {
		return module.toString();
	}

	public Module getModule() {
		return module;
	}

}
