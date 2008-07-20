package org.jdmp.gui.matrix.actions;

import javax.swing.Action;
import javax.swing.JComponent;

import org.jdmp.gui.matrix.MatrixGUIObject;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.HasMatrixList;

public class SumAction extends MatrixAction {

	private static final long serialVersionUID = 5704836312852778524L;

	public SumAction(JComponent c, MatrixGUIObject m, HasMatrixList v) {
		super(c, m, v);
		putValue(Action.NAME, "Sum");
		putValue(Action.SHORT_DESCRIPTION, "Calculates the sum of the entries in this matrix");
	}

	@Override
	public Object call() throws MatrixException {
		Matrix result = getMatrixObject().getMatrix().sum(Ret.NEW, getDimension(), true);
		result.showGUI();
		return result;
	}

}
