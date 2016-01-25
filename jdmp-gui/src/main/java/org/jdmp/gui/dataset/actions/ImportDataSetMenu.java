/*
 * Copyright (C) 2008-2016 by Holger Arndt
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

package org.jdmp.gui.dataset.actions;

import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.ujmp.core.interfaces.GUIObject;

public class ImportDataSetMenu extends JMenu {
	private static final long serialVersionUID = 6667097130254443746L;

	public ImportDataSetMenu(JComponent c, GUIObject o) {
		super("DataSet");
		setMnemonic(KeyEvent.VK_D);
		add(new JMenuItem(new ImportDataSetFromFileAction(c, o)));
		add(new JMenuItem(new ImportDataSetFromClipboardAction(c, o)));
		add(new JMenuItem(new ImportDataSetFromURLAction(c, o)));
		add(new JMenuItem(new ImportDataSetFromDatabaseAction(c, o)));
	}
}
