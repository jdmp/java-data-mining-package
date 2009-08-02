package org.jdmp.gui.sample;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.JMenu;

import org.jdmp.gui.sample.actions.SampleActions;
import org.ujmp.gui.util.DefaultMenuBar;

public class SampleMenuBar extends DefaultMenuBar {
	private static final long serialVersionUID = -2293114754729075707L;

	public SampleMenuBar(JComponent component, SampleGUIObject o) {
		super(component, o);
		JMenu menu = new JMenu("" + o.getClass().getSimpleName());
		List<JComponent> actions = new SampleActions(component, o);
		for (JComponent c : actions) {
			menu.add(c);
		}
		add(menu);
		init(component, o);
	}

}
