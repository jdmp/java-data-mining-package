package org.jdmp.gui.matrix.actions;

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.jdmp.gui.matrix.MatrixGUIObject;
import org.jdmp.matrix.interfaces.HasMatrixList;

public class ClearMatrixAction extends MatrixAction {
	private static final long serialVersionUID = 4460357277537577412L;

	public ClearMatrixAction(JComponent c, MatrixGUIObject m, HasMatrixList v) {
		super(c, m, v);
		putValue(Action.NAME, "Clear");
		putValue(Action.SHORT_DESCRIPTION, "Sets all entries to 0");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0));
	}

	@Override
	public Object call() {
		getMatrixObject().getMatrix().clear();
		return null;
	}

}
