package org.jdmp.gui.sample.actions;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import org.jdmp.core.sample.HasSampleList;
import org.jdmp.gui.actions.ExitAction;
import org.ujmp.core.interfaces.GUIObject;

public class SampleListActions extends ArrayList<JComponent> {
	private static final long serialVersionUID = 864758348092666603L;

	public SampleListActions(JComponent c, HasSampleList v) {
		this.add(new JMenuItem(new AddSampleAction(c, v)));

		this.add(new JSeparator());
		this.add(new JMenuItem(new ExitAction(c, (GUIObject) v)));
	}

}
