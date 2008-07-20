package org.jdmp.gui.actions;

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.ujmp.core.interfaces.GUIObject;

public class ClearAction extends ObjectAction {
	private static final long serialVersionUID = 8394347761552694383L;

	public ClearAction(JComponent c, GUIObject o) {
		super(c, o);
		putValue(Action.NAME, "Clear");
		putValue(Action.SHORT_DESCRIPTION, "Delete the contents of this object");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0));
	}

	public Object call() {
		getObject().clear();
		return getObject();
	}

}
