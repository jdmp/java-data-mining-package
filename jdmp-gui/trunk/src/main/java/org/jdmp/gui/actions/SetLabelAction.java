package org.jdmp.gui.actions;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import org.jdmp.matrix.interfaces.GUIObject;

public class SetLabelAction extends ObjectAction {
	private static final long serialVersionUID = 8660922548207382801L;

	public SetLabelAction(JComponent c, GUIObject o) {
		super(c, o);
		putValue(Action.NAME, "Set Label...");
		putValue(Action.SHORT_DESCRIPTION, "Change the name of this object");
	}

	public Object call() {
		String label = JOptionPane.showInputDialog("Enter the new Label for this Object:",
				getObject().getLabel());
		getObject().setLabel(label);
		return null;
	}

}
