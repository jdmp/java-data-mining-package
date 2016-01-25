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

package org.jdmp.gui.module.actions;

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.jdmp.core.module.DefaultModule;
import org.jdmp.core.module.HasModuleMap;

public class AddLocalModuleAction extends ModuleListAction {
	private static final long serialVersionUID = 5647028718703205997L;

	public AddLocalModuleAction(JComponent c, HasModuleMap i) {
		super(c, i);
		putValue(Action.NAME, "Add LocalModule");
		putValue(Action.SHORT_DESCRIPTION, "Add a new LocalModule");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_L, 0));
	}

	public Object call() {
		getIModules().getModuleMap().add(new DefaultModule());
		return null;
	}

}
