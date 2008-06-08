package org.jdmp.gui.matrix.actions;

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.jdmp.gui.matrix.MatrixGUIObject;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.interfaces.HasMatrixList;

public class FadeInAction extends MatrixAction {
	private static final long serialVersionUID = -485768320588021515L;

	public FadeInAction(JComponent c, MatrixGUIObject m, HasMatrixList v) {
		super(c, m, v);
		putValue(Action.NAME, "Fade In");
		putValue(Action.SHORT_DESCRIPTION, "multiplies all entries with an increasing factor");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_I, 0));
	}

	@Override
	public Object call() throws MatrixException {
		getMatrixObject().getMatrix().fadeIn_();
		return getMatrixObject();
	}
}
