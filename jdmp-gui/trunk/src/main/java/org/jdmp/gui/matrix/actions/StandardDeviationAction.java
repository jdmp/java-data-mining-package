package org.jdmp.gui.matrix.actions;

import javax.swing.Action;
import javax.swing.JComponent;

import org.jdmp.gui.matrix.MatrixGUIObject;
import org.ujmp.core.Matrix;
import org.ujmp.core.doublecalculation.Calculation.Ret;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.HasMatrixList;

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
