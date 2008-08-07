package org.jdmp.gui.matrix.actions;

import javax.swing.Action;
import javax.swing.JComponent;

import org.jdmp.gui.matrix.MatrixGUIObject;
import org.ujmp.core.Matrix;
import org.ujmp.core.doublecalculation.Calculation.Ret;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.HasMatrixList;

public class MinAction extends MatrixAction {
	private static final long serialVersionUID = -1016347929634747390L;

	public MinAction(JComponent c, MatrixGUIObject m, HasMatrixList v) {
		super(c, m, v);
		putValue(Action.NAME, "Min");
		putValue(Action.SHORT_DESCRIPTION, "Calculates the min of the entries in this matrix");
	}

	@Override
	public Object call() throws MatrixException {
		Matrix result = getMatrixObject().getMatrix().min(Ret.NEW, getDimension());
		result.showGUI();
		return result;
	}

}
