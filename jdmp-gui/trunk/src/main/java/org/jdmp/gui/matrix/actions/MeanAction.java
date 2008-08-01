package org.jdmp.gui.matrix.actions;

import javax.swing.Action;
import javax.swing.JComponent;

import org.jdmp.gui.matrix.MatrixGUIObject;
import org.ujmp.core.Matrix;
import org.ujmp.core.doublecalculation.Calculation.Ret;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.HasMatrixList;

public class MeanAction extends MatrixAction {
	private static final long serialVersionUID = 9202273744246930760L;

	public MeanAction(JComponent c, MatrixGUIObject m, HasMatrixList v) {
		super(c, m, v);
		putValue(Action.NAME, "Mean");
		putValue(Action.SHORT_DESCRIPTION, "Calculates the mean of the entries in this matrix");
	}

	@Override
	public Object call() throws MatrixException {
		Matrix result = getMatrixObject().getMatrix().mean(Ret.NEW, getDimension(), true);
		result.showGUI();
		return result;
	}

}
