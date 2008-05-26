package org.jdmp.gui.matrix.actions;

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.jdmp.core.matrix.MatrixGUIObject;
import org.jdmp.core.variable.WorkspaceVariable;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.interfaces.HasMatrixList;

public class LinkMatrixAction extends MatrixAction {
	private static final long serialVersionUID = -3226372238357337081L;

	public LinkMatrixAction(JComponent c, MatrixGUIObject m, HasMatrixList v) {
		super(c, m, v);
		putValue(Action.NAME, "Link Matrix");
		putValue(Action.SHORT_DESCRIPTION, "links the content of this matrix to a new matrix");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_L, 0));
	}

	@Override
	public Object call() throws MatrixException {
		Matrix m = getMatrixObject().getMatrix().link();
		WorkspaceVariable.getInstance().addMatrix(m);
		m.showGUI();
		return m;
	}

}
