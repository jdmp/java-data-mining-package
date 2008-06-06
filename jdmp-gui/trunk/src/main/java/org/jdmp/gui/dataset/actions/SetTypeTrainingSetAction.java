package org.jdmp.gui.dataset.actions;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.jdmp.core.dataset.DataSetType;
import org.jdmp.gui.dataset.DataSetGUIObject;

public class SetTypeTrainingSetAction extends DataSetAction {
	private static final long serialVersionUID = -8333974916070153778L;

	public SetTypeTrainingSetAction(JComponent c, DataSetGUIObject ds) {
		super(c, ds);
		putValue(Action.NAME, "Training Set");
		putValue(Action.SHORT_DESCRIPTION, "Set Type to Training Set");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_T,
				InputEvent.ALT_DOWN_MASK));
	}

	public Object call() {
		getDataSet().getDataSet().setDataSetType(DataSetType.TRAININGSET);
		return null;
	}

}
