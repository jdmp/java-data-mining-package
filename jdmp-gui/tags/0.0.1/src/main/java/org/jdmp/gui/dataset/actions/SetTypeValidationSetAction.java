package org.jdmp.gui.dataset.actions;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.dataset.DataSetType;

public class SetTypeValidationSetAction extends DataSetAction {
	private static final long serialVersionUID = 7064348138705512648L;

	public SetTypeValidationSetAction(JComponent c, DataSet ds) {
		super(c, ds);
		putValue(Action.NAME, "Validation Set");
		putValue(Action.SHORT_DESCRIPTION, "Set Type to Validation Set");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.ALT_DOWN_MASK));
	}

	public Object call() {
		getDataSet().setDataSetType(DataSetType.VALIDATIONSET);
		return null;
	}

}
