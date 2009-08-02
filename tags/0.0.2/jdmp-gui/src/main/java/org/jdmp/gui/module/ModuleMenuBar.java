package org.jdmp.gui.module;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.JMenu;

import org.jdmp.gui.module.actions.ModuleActions;
import org.ujmp.gui.util.DefaultMenuBar;

public class ModuleMenuBar extends DefaultMenuBar {
	private static final long serialVersionUID = 4019937863391146452L;

	public ModuleMenuBar(JComponent component, ModuleGUIObject o) {
		super(component, o);
		JMenu menu = new JMenu("" + o.getClass().getSimpleName());
		List<JComponent> actions = new ModuleActions(component, o);
		for (JComponent c : actions) {
			menu.add(c);
		}
		add(menu);
		init(component, o);
	}

}
