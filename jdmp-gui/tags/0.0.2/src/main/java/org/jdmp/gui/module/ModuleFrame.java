package org.jdmp.gui.module;

import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.gui.util.AbstractFrame;

public class ModuleFrame extends AbstractFrame {
	private static final long serialVersionUID = 3669267793401815259L;

	public ModuleFrame(ModuleGUIObject m) throws MatrixException {
		super(m, new ModulePanel(m));
	}

}
