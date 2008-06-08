package org.jdmp.gui.matrix.actions;

import javax.swing.Action;
import javax.swing.JComponent;

import org.jdmp.gui.matrix.MatrixGUIObject;
import org.jdmp.matrix.calculation.Calculation.Ret;
import org.jdmp.matrix.calculation.general.missingvalues.ImputeMean;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.interfaces.HasMatrixList;

public class ReplaceByMeanAction extends MatrixAction {
	private static final long serialVersionUID = -7820090923370035750L;

	public ReplaceByMeanAction(JComponent c, MatrixGUIObject m, HasMatrixList v) {
		super(c, m, v);
		putValue(Action.NAME, "Replace by mean");
		putValue(Action.SHORT_DESCRIPTION, "Replaces all missing values with the mean");
	}

	@Override
	public Object call() throws MatrixException {
		return getMatrixObject().getMatrix().calc(
				new ImputeMean(getDimension(), getMatrixObject().getMatrix()), Ret.ORIG);
	}

}
