package org.jdmp.gui.actions;

import java.util.logging.Level;

import javax.swing.Action;
import javax.swing.JComponent;

import org.jdmp.core.util.AbstractGUIObject;

public class PrintAction extends ObjectAction {
	private static final long serialVersionUID = 5977367168891486978L;

	public PrintAction(JComponent c, AbstractGUIObject o) {
		super(c, o);
		putValue(Action.NAME, "Print...");
		putValue(Action.SHORT_DESCRIPTION, "Print a screenshot of this object");
	}

	public Object call() {
		if (getComponent() == null) {
			logger.log(Level.WARNING, "no panel provided for printing");
		} else {
			// PrintPreviewDialog ppd = new PrintPreviewDialog(getPanel());
			// ppd.setVisible(true);
		}
		return null;
	}

}
