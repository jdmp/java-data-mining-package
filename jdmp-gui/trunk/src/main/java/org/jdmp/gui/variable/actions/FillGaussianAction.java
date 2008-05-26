package org.jdmp.gui.variable.actions;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.jdmp.core.variable.Variable;
import org.jdmp.matrix.MatrixException;

public class FillGaussianAction extends VariableAction {
	private static final long serialVersionUID = 2702306358440290066L;

	public FillGaussianAction(JComponent c, Variable v) {
		super(c, v);
		putValue(Action.NAME, "Fill Gaussian");
		putValue(Action.SHORT_DESCRIPTION, "Fills all values with gaussian noise");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_G);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.ALT_DOWN_MASK));
	}

	public Object call() throws MatrixException {
		getVariable().fillGaussian();
		return null;
	}

}
