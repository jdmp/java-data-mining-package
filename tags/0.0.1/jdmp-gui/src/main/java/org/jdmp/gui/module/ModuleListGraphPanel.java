package org.jdmp.gui.module;

import org.jdmp.core.module.HasModules;
import org.jdmp.gui.util.JungGraphPanel;

public class ModuleListGraphPanel extends JungGraphPanel {
	private static final long serialVersionUID = -3953580667762467583L;

	public ModuleListGraphPanel(HasModules iModules) {

		setGraph(new ModuleListGraphWrapper(iModules));

	}

}
