package org.jdmp.gui.variable.actions;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.jdmp.gui.variable.VariableGUIObject;
import org.ujmp.core.exceptions.MatrixException;

public class DivideAction extends VariableAction {
	private static final long serialVersionUID = -3064208499759728589L;

	public DivideAction(JComponent c, VariableGUIObject v) {
		super(c, v);
		putValue(Action.NAME, "Divide...");
		putValue(Action.SHORT_DESCRIPTION, "Divide all values in this variable");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_D,
				InputEvent.ALT_DOWN_MASK));
	}

	public Object call() throws MatrixException {
		getVariable().getVariable().addMatrix(getVariable().getVariable().getMatrix().divide(2.0));
		return null;
	}

}
