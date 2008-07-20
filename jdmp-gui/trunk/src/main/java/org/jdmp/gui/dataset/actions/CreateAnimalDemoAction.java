package org.jdmp.gui.dataset.actions;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.dataset.DataSetFactory;
import org.jdmp.gui.dataset.DataSetGUIObject;
import org.ujmp.core.exceptions.MatrixException;

public class CreateAnimalDemoAction extends DataSetAction {
	private static final long serialVersionUID = 6821548758392591613L;

	public CreateAnimalDemoAction(JComponent c, DataSetGUIObject ds) {
		super(c, ds);
		putValue(Action.NAME, "Create Animal DataSet");
		putValue(Action.SHORT_DESCRIPTION, "Creates a demo DataSet with animals");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_A,
				InputEvent.ALT_DOWN_MASK));
	}

	public Object call() throws MatrixException {
		DataSet animals = DataSetFactory.ANIMALS();
		animals.showGUI();
		return animals;
	}

}
