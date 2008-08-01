package org.jdmp.gui.matrix.actions;

import javax.swing.Action;
import javax.swing.JComponent;

import org.jdmp.gui.matrix.MatrixGUIObject;
import org.ujmp.core.doublecalculation.Calculation.Ret;
import org.ujmp.core.doublecalculation.general.missingvalues.ImputeKNN;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.HasMatrixList;

public class ReplaceByNearestNeighborAction extends MatrixAction {
	private static final long serialVersionUID = -2401692333851059830L;

	public ReplaceByNearestNeighborAction(JComponent c, MatrixGUIObject m, HasMatrixList v) {
		super(c, m, v);
		putValue(Action.NAME, "Replace by nearest neighbor");
		putValue(Action.SHORT_DESCRIPTION, "Replaces all missing values with the nearest neighbor");
	}

	@Override
	public Object call() throws MatrixException {
		return getMatrixObject().getMatrix().calc(new ImputeKNN(getMatrixObject().getMatrix(), 3),
				Ret.ORIG);
	}

}
