package org.jdmp.gui.matrix.actions;

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.jdmp.core.matrix.MatrixGUIObject;
import org.jdmp.core.variable.WorkspaceVariable;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.interfaces.HasMatrixList;

public class CopyMatrixAction extends MatrixAction {
	private static final long serialVersionUID = 6122103774731476379L;

	public CopyMatrixAction(JComponent c, MatrixGUIObject m, HasMatrixList v) {
		super(c, m, v);
		putValue(Action.NAME, "Copy Matrix");
		putValue(Action.SHORT_DESCRIPTION, "copy the content of this matrix to a new matrix");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_C, 0));
	}

	@Override
	public Object call() {
		Matrix m = getMatrixObject().getMatrix().clone();
		WorkspaceVariable.getInstance().addMatrix(m);
		m.showGUI();
		return m;
	}

}
