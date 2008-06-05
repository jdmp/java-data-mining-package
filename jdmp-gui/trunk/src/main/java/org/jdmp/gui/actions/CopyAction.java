package org.jdmp.gui.actions;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.util.logging.Level;

import javax.swing.Action;
import javax.swing.JComponent;

import org.jdmp.core.matrix.MatrixGUIObject;
import org.jdmp.gui.clipboard.MatrixSelection;
import org.jdmp.matrix.interfaces.GUIObject;

public class CopyAction extends ObjectAction {
	private static final long serialVersionUID = -2679630812122400202L;

	public CopyAction(JComponent c, GUIObject o) {
		super(c, o);
		putValue(Action.NAME, "Copy");
		if (o == null) {
			putValue(Action.SHORT_DESCRIPTION, "Copy Object to clipboard");
		} else {
			putValue(Action.SHORT_DESCRIPTION, "Copy " + o.getClass().getSimpleName()
					+ " to clipboard");
		}
	}

	public Object call() {
		try {
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

			if (getObject() instanceof MatrixGUIObject) {
				MatrixSelection ms = new MatrixSelection(((MatrixGUIObject) getObject())
						.getMatrix());
				clipboard.setContents(ms, ms);
			} else {
			}
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not copy to clipboard", e);
		}
		return null;
	}

}
