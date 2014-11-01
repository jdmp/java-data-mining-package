/*
 * Copyright (C) 2008-2014 by Holger Arndt
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

package org.jdmp.gui.variable.actions;

import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.jdmp.core.variable.HasVariableMap;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.gui.actions.AbstractObjectAction;

public class NewEmptyVariableAction extends AbstractObjectAction {
	private static final long serialVersionUID = 153544541533260702L;

	public NewEmptyVariableAction(JComponent c, GUIObject i) {
		super(c, i);
		putValue(Action.NAME, "Empty Variable");
		putValue(Action.SHORT_DESCRIPTION, "Create a new empty Variable");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
		putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.ALT_DOWN_MASK));
	}

	public Object call() {
		Variable v = Variable.Factory.emptyVariable();
		if (getCoreObject() instanceof HasVariableMap) {
			try {
				((HasVariableMap) getCoreObject()).getVariableMap().add(v);
			} catch (Exception e) {
				v.showGUI();
			}
		} else {
			v.showGUI();
		}
		return v;
	}

}
