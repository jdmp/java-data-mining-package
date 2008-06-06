package org.jdmp.gui.dataset.actions;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.jdmp.core.dataset.DataSetType;
import org.jdmp.gui.dataset.DataSetGUIObject;

public class SetTypeNoneAction extends DataSetAction {
	private static final long serialVersionUID = -596740549597622757L;

	public SetTypeNoneAction(JComponent c, DataSetGUIObject ds) {
		super(c, ds);
		putValue(Action.NAME, "None");
		putValue(Action.SHORT_DESCRIPTION, "Set Type to None");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N,
				InputEvent.ALT_DOWN_MASK));
	}

	public Object call() {
		getDataSet().getDataSet().setDataSetType(DataSetType.NONE);
		return null;
	}

}
