package org.jdmp.gui.dataset.actions;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import org.jdmp.gui.actions.ExitAction;
import org.jdmp.gui.actions.ObjectActions;
import org.jdmp.gui.dataset.DataSetGUIObject;

public class DataSetActions extends ObjectActions {
	private static final long serialVersionUID = 989312444370800368L;

	public DataSetActions(JComponent c, DataSetGUIObject ds) {
		super(c, ds);

		add(2, new JSeparator());

		add(new JMenuItem(new CreateAnimalDemoAction(c, ds)));
		this.add(new JSeparator());
		this.add(new JMenuItem(new ExitAction(c, ds)));
	}

}
