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

package org.jdmp.gui.algorithm;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.JMenu;

import org.jdmp.gui.algorithm.actions.AlgorithmActions;
import org.ujmp.gui.menu.DefaultMenuBar;

public class AlgorithmMenuBar extends DefaultMenuBar {
	private static final long serialVersionUID = -6724827875524793049L;

	public AlgorithmMenuBar(JComponent component, AlgorithmGUIObject o) {
		super(component, o);
		JMenu menu = new JMenu("" + o.getClass().getSimpleName());
		List<JComponent> actions = new AlgorithmActions(component, o);
		for (JComponent c : actions) {
			menu.add(c);
		}
		add(menu);
		init(component, o);
	}

}
