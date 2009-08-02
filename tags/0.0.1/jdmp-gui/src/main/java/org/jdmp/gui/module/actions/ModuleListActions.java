package org.jdmp.gui.module.actions;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JMenuItem;

import org.jdmp.core.module.HasModules;

public class ModuleListActions extends ArrayList<JComponent> {
	private static final long serialVersionUID = 1050856609167050893L;

	public ModuleListActions(JComponent c, HasModules i) {
		add(new JMenuItem(new AddLocalModuleAction(c, i)));
		add(new JMenuItem(new RemoveAllModulesAction(c, i)));
	}
}
