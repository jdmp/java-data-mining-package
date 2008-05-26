package org.jdmp.gui.matrix.actions;

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.jdmp.core.matrix.MatrixGUIObject;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.interfaces.HasMatrixList;

public class FadeOutAction extends MatrixAction {
	private static final long serialVersionUID = 7628774721821715864L;

	public FadeOutAction(JComponent c, MatrixGUIObject m, HasMatrixList v) {
		super(c, m, v);
		putValue(Action.NAME, "Fade Out");
		putValue(Action.SHORT_DESCRIPTION, "multiplies all entries with an decreasing factor");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, 0));
	}

	@Override
	public Object call() throws MatrixException {
		getMatrixObject().getMatrix().fadeOut_();
		return getMatrixObject();
	}
}
