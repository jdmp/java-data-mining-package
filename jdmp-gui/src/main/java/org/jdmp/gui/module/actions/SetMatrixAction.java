/*
 * Copyright (C) 2008-2013 by Holger Arndt
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

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.jdmp.gui.module.ModuleGUIObject;
import org.ujmp.core.Matrix;

public class SetMatrixAction extends ModuleAction {
	private static final long serialVersionUID = -3268204362414608614L;

	private Matrix matrix = null;

	private int index = 0;

	public SetMatrixAction(JComponent c, ModuleGUIObject module, int index, Matrix m) {
		super(c, module);
		this.matrix = m;
		this.index = index;
		putValue(Action.NAME, "Set Matrix");
		putValue(Action.SHORT_DESCRIPTION, "Sets the Matrix to a specified value");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S,
				InputEvent.ALT_DOWN_MASK));
	}

	
	public Object call() {
		// if (getModule() != null) {
		// getModule().setMatrixForVariableReference(reference, index, matrix);
		// }
		return null;
	}

}
