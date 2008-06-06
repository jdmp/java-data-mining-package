package org.jdmp.gui.dataset.actions;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.jdmp.core.dataset.DataSetType;
import org.jdmp.gui.dataset.DataSetGUIObject;

public class SetTypeTestSetAction extends DataSetAction {
	private static final long serialVersionUID = 3742838664165915817L;

	public SetTypeTestSetAction(JComponent c, DataSetGUIObject ds) {
		super(c, ds);
		putValue(Action.NAME, "Test Set");
		putValue(Action.SHORT_DESCRIPTION, "Set Type to Test Set");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_E,
				InputEvent.ALT_DOWN_MASK));
	}

	public Object call() {
		getDataSet().getDataSet().setDataSetType(DataSetType.TESTSET);
		return null;
	}

}
