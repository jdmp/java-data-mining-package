package org.jdmp.gui.matrix.actions;

import javax.swing.Action;
import javax.swing.JComponent;

import org.jdmp.gui.matrix.MatrixGUIObject;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.calculation.Calculation.Ret;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.interfaces.HasMatrixList;

public class StandardDeviationAction extends MatrixAction {
	private static final long serialVersionUID = -2467432732455863504L;

	public StandardDeviationAction(JComponent c, MatrixGUIObject m, HasMatrixList v) {
		super(c, m, v);
		putValue(Action.NAME, "Standard Deviation");
		putValue(Action.SHORT_DESCRIPTION,
				"Calculates the standard deviation of the entries in this matrix");
	}

	@Override
	public Object call() throws MatrixException {
		Matrix result = getMatrixObject().getMatrix().std(Ret.NEW, getDimension(), true);
		result.showGUI();
		return result;
	}

}
