package org.jdmp.gui.matrix.actions;

import javax.swing.Action;
import javax.swing.JComponent;

import org.jdmp.core.matrix.MatrixGUIObject;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.Calculation.Ret;
import org.jdmp.matrix.interfaces.HasMatrixList;

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
