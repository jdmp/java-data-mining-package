package org.jdmp.gui.variable;

import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.gui.util.AbstractFrame;

public class VariableFrame extends AbstractFrame {
	private static final long serialVersionUID = -7217906612334653535L;

	public VariableFrame(VariableGUIObject v) throws MatrixException {
		super(v, new VariablePanel(v));
	}

}
