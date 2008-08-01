package org.jdmp.gui.matrix.actions;

import javax.swing.Action;
import javax.swing.JComponent;

import org.jdmp.gui.matrix.MatrixGUIObject;
import org.ujmp.core.doublecalculation.Calculation.Ret;
import org.ujmp.core.doublecalculation.general.missingvalues.ImputeMean;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.HasMatrixList;

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
