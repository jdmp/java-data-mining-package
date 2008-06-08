package org.jdmp.gui.matrix.actions;

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.jdmp.gui.matrix.MatrixGUIObject;
import org.jdmp.matrix.interfaces.HasMatrixList;

public class FillWithValueAction extends MatrixAction {
	private static final long serialVersionUID = 6318874871015478768L;

	private String initialValue = "";

	public FillWithValueAction(JComponent c, MatrixGUIObject m, HasMatrixList v) {
		this(c, m, v, "");
	}

	public FillWithValueAction(JComponent c, MatrixGUIObject m, HasMatrixList v, String initialValue) {
		super(c, m, v);
		this.initialValue = initialValue;
		putValue(Action.NAME, "Fill with value");
		putValue(Action.SHORT_DESCRIPTION, "sets all entries to the same value");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_F);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F, 0));
	}

	@Override
	public Object call() {
		double value = 0;
		String s = JOptionPane.showInputDialog("Enter value:", initialValue);
		try {
			value = Double.parseDouble(s);
			getMatrixObject().setEntriesTo_(value);
		} catch (Exception ex) {
		}
		return null;
	}

}
