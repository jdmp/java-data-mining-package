/*
 * Copyright (C) 2008-2010 by Holger Arndt
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

import javax.swing.Action;
import javax.swing.JComponent;

import org.jdmp.gui.module.ModuleGUIObject;

public class ScriptAction extends ModuleAction {
	private static final long serialVersionUID = -7530054655486550744L;

	private String script = null;

	public ScriptAction(JComponent c, ModuleGUIObject module, String name, String description,
			String script) {
		super(c, module);
		this.script = script;
		putValue(Action.NAME, name);
		putValue(Action.SHORT_DESCRIPTION, description);
	}

	
	public Object call() {
		try {
			return getModule().getCommandWindow().execute(script);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
