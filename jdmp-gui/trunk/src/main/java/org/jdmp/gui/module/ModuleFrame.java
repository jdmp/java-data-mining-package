package org.jdmp.gui.module;

import org.jdmp.core.module.AbstractModule;
import org.jdmp.gui.util.AbstractFrame;
import org.jdmp.matrix.exceptions.MatrixException;

public class ModuleFrame extends AbstractFrame {
	private static final long serialVersionUID = 3669267793401815259L;

	private static ModuleFrame moduleFrame = null;

	public static ModuleFrame getInstance() throws MatrixException {
		if (moduleFrame == null) {
			moduleFrame = new ModuleFrame((ModuleGUIObject) AbstractModule.getInstance()
					.getGUIObject());
		}
		return moduleFrame;
	}

	public ModuleFrame(ModuleGUIObject m) throws MatrixException {
		super(m);
	}

}
