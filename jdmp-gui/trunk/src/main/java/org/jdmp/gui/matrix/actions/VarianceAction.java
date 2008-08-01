package org.jdmp.gui.matrix.actions;

import javax.swing.Action;
import javax.swing.JComponent;

import org.jdmp.gui.matrix.MatrixGUIObject;
import org.ujmp.core.Matrix;
import org.ujmp.core.doublecalculation.Calculation.Ret;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.HasMatrixList;

public class VarianceAction extends MatrixAction {
	private static final long serialVersionUID = -2308191750274084929L;

	public VarianceAction(JComponent c, MatrixGUIObject m, HasMatrixList v) {
		super(c, m, v);
		putValue(Action.NAME, "Variance");
		putValue(Action.SHORT_DESCRIPTION, "Calculates the variance of the entries in this matrix");
	}

	@Override
	public Object call() throws MatrixException {
		Matrix result = getMatrixObject().getMatrix().var(Ret.NEW, getDimension(), true);
		result.showGUI();
		return result;
	}

}
