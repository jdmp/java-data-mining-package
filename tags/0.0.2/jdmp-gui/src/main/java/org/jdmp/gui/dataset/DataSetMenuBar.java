package org.jdmp.gui.dataset;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.JMenu;

import org.jdmp.gui.dataset.actions.DataSetActions;
import org.ujmp.gui.util.DefaultMenuBar;

public class DataSetMenuBar extends DefaultMenuBar {
	private static final long serialVersionUID = 7982201026132757168L;

	public DataSetMenuBar(JComponent component, DataSetGUIObject o) {
		super(component, o);
		JMenu menu = new JMenu("" + o.getClass().getSimpleName());
		List<JComponent> actions = new DataSetActions(component, o);
		for (JComponent c : actions) {
			menu.add(c);
		}
		add(menu);
		init(component, o);
	}

}
