package org.jdmp.gui.module;

import org.jdmp.core.module.Module;
import org.jdmp.gui.util.AbstractFrame;
import org.jdmp.matrix.exceptions.MatrixException;

public class ModuleFrame extends AbstractFrame {
	private static final long serialVersionUID = 3669267793401815259L;

	private static ModuleFrame moduleFrame = null;

	public static ModuleFrame getInstance() throws MatrixException {
		if (moduleFrame == null) {
			moduleFrame = new ModuleFrame(Module.getInstance());
		}
		return moduleFrame;
	}

	public ModuleFrame(Module m) throws MatrixException {
		super(m);
	}

}
