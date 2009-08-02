package org.jdmp.gui.dataset.actions;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import org.jdmp.gui.dataset.DataSetGUIObject;
import org.ujmp.gui.actions.CopyAction;
import org.ujmp.gui.actions.ExitAction;
import org.ujmp.gui.actions.PasteAction;

public class DataSetActions extends ArrayList<JComponent> {
	private static final long serialVersionUID = 989312444370800368L;

	public DataSetActions(JComponent component, DataSetGUIObject ds) {
		add(new JMenuItem(new CreateAnimalDemoAction(component, ds)));
		this.add(new JSeparator());
		this.add(new JMenuItem(new ExitAction(component, ds)));

		add(new JMenuItem(new CopyAction(component, ds)));
		add(new JMenuItem(new PasteAction(component, ds)));

	}

}
