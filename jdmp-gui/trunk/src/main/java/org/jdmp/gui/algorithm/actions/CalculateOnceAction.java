package org.jdmp.gui.algorithm.actions;

import javax.swing.Action;
import javax.swing.JComponent;

import org.jdmp.gui.algorithm.AlgorithmGUIObject;
import org.ujmp.core.Matrix;

public class CalculateOnceAction extends AlgorithmAction {
	private static final long serialVersionUID = 2007146067856422375L;

	private Matrix matrix = null;

	public CalculateOnceAction(JComponent c, AlgorithmGUIObject a, Matrix m) {
		super(c, a);
		this.matrix = m;
		putValue(Action.NAME, a.getLabel());
		putValue(Action.SHORT_DESCRIPTION, "Executes this algorithm once");
	}

	public CalculateOnceAction(JComponent c, AlgorithmGUIObject a) {
		super(c, a);
		putValue(Action.NAME, "Calculate once");
		putValue(Action.SHORT_DESCRIPTION, "Executes this algorithm once");
	}

	public Object call() throws Exception {
		if (matrix == null) {
			getAlgorithm().getAlgorithm().calculate();
		} else {
			getAlgorithm().getAlgorithm().calculate(matrix);
		}
		return null;
	}

}
