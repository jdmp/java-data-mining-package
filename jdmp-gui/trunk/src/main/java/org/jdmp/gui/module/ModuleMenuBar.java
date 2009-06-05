/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
 *
 * This file is part of the Java Data Mining Package (JDMP).
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership and licensing.
 *
 * JDMP is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * JDMP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with JDMP; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.jdmp.gui.module;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import org.jdmp.core.algorithm.basic.About;
import org.jdmp.core.algorithm.basic.Copy;
import org.jdmp.core.algorithm.basic.CreateHenon;
import org.jdmp.core.algorithm.basic.CreateIris;
import org.jdmp.core.algorithm.basic.Help;
import org.jdmp.core.algorithm.basic.Paste;
import org.jdmp.core.algorithm.basic.ShowLicense;
import org.jdmp.core.util.JDMPPluginsMatrix;
import org.jdmp.gui.module.actions.ModuleActions;
import org.jdmp.gui.module.actions.ScriptAction;
import org.ujmp.gui.actions.ShowInFrameAction;
import org.ujmp.gui.menu.DefaultMenuBar;

public class ModuleMenuBar extends DefaultMenuBar {
	private static final long serialVersionUID = 4019937863391146452L;

	public ModuleMenuBar(ModuleGUIObject o) {
		super(o);
		JMenu menu = new JMenu("Module");
		List<JComponent> actions = new ModuleActions(null, o);
		for (JComponent c : actions) {
			menu.add(c);
		}
		add(menu);

		JMenu edit = new JMenu("Edit");
		edit.add(new ScriptAction(null, o, "Copy", Copy.DESCRIPTION, "copy(ans)"));
		edit.add(new ScriptAction(null, o, "Paste", Paste.DESCRIPTION, "paste"));
		add(edit);

		toolsMenu.add(new JMenuItem(new ShowInFrameAction(null, new JDMPPluginsMatrix())));

		init(o);
		examplesMenu.add(new JSeparator());
		examplesMenu.add(new ScriptAction(null, o, "Iris DataSet", CreateIris.DESCRIPTION,
				"dataset=iris"));
		examplesMenu.add(new ScriptAction(null, o, "Henon Map DataSet", CreateHenon.DESCRIPTION,
				"dataset=henon"));

		JMenu help = new JMenu("Help");
		help.add(new ScriptAction(null, o, "Help", Help.DESCRIPTION, "help"));
		help.add(new ScriptAction(null, o, "About", About.DESCRIPTION, "about"));
		help.add(new ScriptAction(null, o, "License", ShowLicense.DESCRIPTION, "license"));
		add(help);

	}

}
