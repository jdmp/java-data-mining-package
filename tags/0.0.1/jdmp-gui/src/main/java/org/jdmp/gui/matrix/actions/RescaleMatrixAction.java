package org.jdmp.gui.matrix.actions;

import javax.swing.Action;
import javax.swing.JComponent;

import org.jdmp.core.matrix.MatrixGUIObject;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.interfaces.HasMatrixList;

public class RescaleMatrixAction extends MatrixAction {
	private static final long serialVersionUID = -4509186928254414609L;

	public RescaleMatrixAction(JComponent c, MatrixGUIObject m, HasMatrixList v) {
		super(c, m, v);
		putValue(Action.NAME, "Rescale Matrix");
		putValue(Action.SHORT_DESCRIPTION, "scales all entries to values between -1 and 1");
	}

	@Override
	public Object call() throws MatrixException {
		getMatrixObject().getMatrix().rescaleEntries_(Matrix.ALL, -1, 1);
		return getMatrixObject();
	}
}
