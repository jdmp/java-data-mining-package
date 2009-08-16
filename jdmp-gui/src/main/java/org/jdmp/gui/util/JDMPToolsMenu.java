package org.jdmp.gui.util;

import javax.swing.JComponent;
import javax.swing.JMenuItem;

import org.jdmp.core.plugin.JDMPPluginsMatrix;
import org.ujmp.gui.actions.ShowInFrameAction;
import org.ujmp.gui.menu.UJMPToolsMenu;

public class JDMPToolsMenu extends UJMPToolsMenu {
	private static final long serialVersionUID = -5218308850039601038L;

	public JDMPToolsMenu(JComponent component) {
		super(component);
		add(new JMenuItem(new ShowInFrameAction(component, new JDMPPluginsMatrix())), 0);
	}

}
