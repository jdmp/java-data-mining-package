package org.jdmp.gui.matrix.actions;

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.jdmp.gui.matrix.MatrixGUIObject;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.interfaces.HasMatrixList;

public class FillUniformAction extends MatrixAction {
	private static final long serialVersionUID = -2169147968755999187L;

	public FillUniformAction(JComponent c, MatrixGUIObject m, HasMatrixList v) {
		super(c, m, v);
		putValue(Action.NAME, "Fill Uniform");
		putValue(Action.SHORT_DESCRIPTION, "set entries to random values between -1.0 and 1.0");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_U, 0));
	}

	@Override
	public Object call() throws MatrixException {
		MatrixGUIObject m = getMatrixObject();
		m.setEntriesUniform_(-1.0, 1.0);
		return m;
	}
}
