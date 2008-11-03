/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
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
