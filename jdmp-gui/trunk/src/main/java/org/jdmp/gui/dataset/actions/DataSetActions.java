package org.jdmp.gui.dataset.actions;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import org.jdmp.core.dataset.DataSet;
import org.jdmp.gui.actions.ExitAction;
import org.jdmp.gui.actions.ObjectActions;

public class DataSetActions extends ObjectActions {
	private static final long serialVersionUID = 989312444370800368L;

	public DataSetActions(JComponent c, DataSet ds) {
		super(c, ds);

		add(2, new JSeparator());
		add(3, new JMenuItem(new ImportCSVInOneLineAction(c, ds)));

		add(new JMenuItem(new CreateAnimalDemoAction(c, ds)));

		JMenu setTypeMenu = new JMenu("Set Type");

		setTypeMenu.add(new SetTypeNoneAction(c, ds));
		setTypeMenu.add(new SetTypeTrainingSetAction(c, ds));
		setTypeMenu.add(new SetTypeTestSetAction(c, ds));
		setTypeMenu.add(new SetTypeValidationSetAction(c, ds));
		add(setTypeMenu);

		this.add(new JSeparator());
		this.add(new JMenuItem(new ExitAction(c, ds)));
	}

}
