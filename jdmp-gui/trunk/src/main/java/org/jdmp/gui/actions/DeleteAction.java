package org.jdmp.gui.actions;

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.jdmp.matrix.interfaces.GUIObject;

public class DeleteAction extends ObjectAction {
	private static final long serialVersionUID = -5156314488187149520L;

	public DeleteAction(JComponent c, GUIObject o) {
		super(c, o);
		putValue(Action.NAME, "Delete");
		putValue(Action.SHORT_DESCRIPTION, "Delete this object");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
	}

	public Object call() {
		getObject().dispose();
		return null;
	}

}
