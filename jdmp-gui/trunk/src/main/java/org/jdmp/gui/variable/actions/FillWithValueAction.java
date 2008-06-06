package org.jdmp.gui.variable.actions;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.jdmp.gui.variable.VariableGUIObject;

public class FillWithValueAction extends VariableAction {
	private static final long serialVersionUID = -3609906043755395315L;

	public FillWithValueAction(JComponent c, VariableGUIObject v) {
		super(c, v);
		putValue(Action.NAME, "Fill With Value...");
		putValue(Action.SHORT_DESCRIPTION, "Fills all values with specified value");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_V,
				InputEvent.ALT_DOWN_MASK));
	}

	public Object call() {
		double value = 0;
		String s = JOptionPane.showInputDialog("Enter value:");
		try {
			value = Double.parseDouble(s);
			getVariable().fillWithValue(value);
		} catch (Exception ex) {
		}
		return null;
	}

}
