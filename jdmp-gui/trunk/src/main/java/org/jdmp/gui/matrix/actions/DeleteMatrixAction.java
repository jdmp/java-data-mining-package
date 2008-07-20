package org.jdmp.gui.matrix.actions;

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.jdmp.gui.matrix.MatrixGUIObject;
import org.ujmp.core.interfaces.HasMatrixList;

public class DeleteMatrixAction extends MatrixAction {
	private static final long serialVersionUID = -6086020959987292649L;

	public DeleteMatrixAction(JComponent c, MatrixGUIObject m, HasMatrixList v) {
		super(c, m, v);
		putValue(Action.NAME, "Delete");
		putValue(Action.SHORT_DESCRIPTION, "Sets all entries to 0");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
	}

	@Override
	public Object call() {
		getMatrixObject().getMatrix().clear();
		return null;
	}

}
