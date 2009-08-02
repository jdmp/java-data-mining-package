package org.jdmp.gui.variable;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.JMenu;

import org.jdmp.gui.variable.actions.VariableActions;
import org.ujmp.gui.util.DefaultMenuBar;

public class VariableMenuBar extends DefaultMenuBar {
	private static final long serialVersionUID = 4537110267416117135L;

	public VariableMenuBar(JComponent component, VariableGUIObject o) {
		super(component, o);
		JMenu menu = new JMenu("" + o.getClass().getSimpleName());
		List<JComponent> actions = new VariableActions(component, o);
		for (JComponent c : actions) {
			menu.add(c);
		}
		add(menu);
		init(component, o);
	}

}