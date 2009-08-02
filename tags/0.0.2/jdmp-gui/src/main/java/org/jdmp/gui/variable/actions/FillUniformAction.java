package org.jdmp.gui.variable.actions;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.jdmp.gui.variable.VariableGUIObject;
import org.ujmp.core.exceptions.MatrixException;

public class FillUniformAction extends VariableAction {
	private static final long serialVersionUID = -3431785036087571881L;

	public FillUniformAction(JComponent c, VariableGUIObject v) {
		super(c, v);
		putValue(Action.NAME, "Fill Uniform");
		putValue(Action.SHORT_DESCRIPTION, "Fills all values with uniform noise");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_U,
				InputEvent.ALT_DOWN_MASK));
	}

	public Object call() throws MatrixException {
		getVariable().getVariable().addMatrix(getVariable().getVariable().getMatrix().rand());
		return null;
	}

}
