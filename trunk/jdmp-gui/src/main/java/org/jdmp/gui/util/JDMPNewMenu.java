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

package org.jdmp.gui.util;

import javax.swing.JComponent;

import org.jdmp.gui.dataset.actions.NewDataSetMenu;
import org.jdmp.gui.module.actions.NewModuleMenu;
import org.jdmp.gui.sample.actions.NewSampleMenu;
import org.jdmp.gui.variable.actions.NewVariableMenu;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.gui.menu.UJMPNewMenu;

public class JDMPNewMenu extends UJMPNewMenu {
	private static final long serialVersionUID = -3730564188631921010L;

	public JDMPNewMenu(JComponent c, GUIObject o) {
		super(c, o);
		add(new NewVariableMenu(c, o));
		add(new NewSampleMenu(c, o));
		add(new NewDataSetMenu(c, o));
		add(new NewModuleMenu(c, o));
	}

}
