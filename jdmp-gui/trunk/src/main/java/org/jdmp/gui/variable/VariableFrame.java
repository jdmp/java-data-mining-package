package org.jdmp.gui.variable;

import org.jdmp.core.variable.Variable;
import org.jdmp.gui.util.AbstractFrame;
import org.jdmp.matrix.MatrixException;

public class VariableFrame extends AbstractFrame {
	private static final long serialVersionUID = -7217906612334653535L;

	public VariableFrame(Variable v) throws MatrixException {
		super(v);
	}

}
