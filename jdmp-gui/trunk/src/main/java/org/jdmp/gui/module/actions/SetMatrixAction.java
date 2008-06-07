package org.jdmp.gui.module.actions;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.jdmp.gui.module.ModuleGUIObject;
import org.jdmp.matrix.Matrix;

public class SetMatrixAction extends ModuleAction {
	private static final long serialVersionUID = -3268204362414608614L;

	private Matrix matrix = null;

	private int index = 0;

	public SetMatrixAction(JComponent c, ModuleGUIObject module, int index, Matrix m) {
		super(c, module);
		this.matrix = m;
		this.index = index;
		putValue(Action.NAME, "Set Matrix");
		putValue(Action.SHORT_DESCRIPTION, "Sets the Matrix to a specified value");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S,
				InputEvent.ALT_DOWN_MASK));
	}

	public Object call() {
		// if (getModule() != null) {
		// getModule().setMatrixForVariableReference(reference, index, matrix);
		// }
		return null;
	}

}
