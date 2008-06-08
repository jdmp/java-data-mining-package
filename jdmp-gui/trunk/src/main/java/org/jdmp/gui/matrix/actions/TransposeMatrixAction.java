package org.jdmp.gui.matrix.actions;

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.jdmp.core.variable.WorkspaceVariable;
import org.jdmp.gui.matrix.MatrixGUIObject;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.interfaces.HasMatrixList;

public class TransposeMatrixAction extends MatrixAction {
	private static final long serialVersionUID = -993725459831614912L;

	public TransposeMatrixAction(JComponent c, MatrixGUIObject m, HasMatrixList v) {
		super(c, m, v);
		putValue(Action.NAME, "Transpose Matrix");
		putValue(Action.SHORT_DESCRIPTION, "transposes this matrix");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_T, 0));
	}

	@Override
	public Object call() throws MatrixException {
		Matrix m = getMatrixObject().getMatrix().transpose();
		WorkspaceVariable.getInstance().addMatrix(m);
		m.showGUI();
		return m;
	}

}
