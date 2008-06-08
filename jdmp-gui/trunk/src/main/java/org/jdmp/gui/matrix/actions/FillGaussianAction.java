package org.jdmp.gui.matrix.actions;

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.jdmp.gui.matrix.MatrixGUIObject;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.interfaces.HasMatrixList;

public class FillGaussianAction extends MatrixAction {
	private static final long serialVersionUID = -8334744425028399100L;

	public FillGaussianAction(JComponent c, MatrixGUIObject m, HasMatrixList v) {
		super(c, m, v);
		putValue(Action.NAME, "Fill Gaussian");
		putValue(Action.SHORT_DESCRIPTION,
				"set entries to gaussian values with mean 0.0 and variance 1.0");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_G);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_G, 0));
	}

	@Override
	public Object call() throws MatrixException {
		MatrixGUIObject m = getMatrixObject();
		m.setEntriesGaussian_(0.0, 1.0);
		return m;
	}

}
