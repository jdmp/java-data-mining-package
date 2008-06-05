package org.jdmp.gui.actions;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.jdmp.matrix.interfaces.GUIObject;

public class ExitAction extends ObjectAction {
	private static final long serialVersionUID = -4139646496876151305L;

	public ExitAction(JComponent c, GUIObject o) {
		super(c, o);
		putValue(Action.NAME, "Exit");
		putValue(Action.SHORT_DESCRIPTION, "Exits the program");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_X,
				InputEvent.ALT_DOWN_MASK));
	}

	public Object call() {
		System.exit(0);
		return null;
	}

}
