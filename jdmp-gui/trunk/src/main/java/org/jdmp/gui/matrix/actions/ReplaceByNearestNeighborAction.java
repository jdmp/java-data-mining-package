package org.jdmp.gui.matrix.actions;

import javax.swing.Action;
import javax.swing.JComponent;

import org.jdmp.gui.matrix.MatrixGUIObject;
import org.jdmp.matrix.calculation.Calculation.Ret;
import org.jdmp.matrix.calculation.general.missingvalues.ImputeKNN;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.interfaces.HasMatrixList;

public class ReplaceByNearestNeighborAction extends MatrixAction {
	private static final long serialVersionUID = -2401692333851059830L;

	public ReplaceByNearestNeighborAction(JComponent c, MatrixGUIObject m, HasMatrixList v) {
		super(c, m, v);
		putValue(Action.NAME, "Replace by nearest neighbor");
		putValue(Action.SHORT_DESCRIPTION, "Replaces all missing values with the nearest neighbor");
	}

	@Override
	public Object call() throws MatrixException {
		return getMatrixObject().getMatrix().calc(
				new ImputeKNN(getDimension(), getMatrixObject().getMatrix()), Ret.ORIG);
	}

}
