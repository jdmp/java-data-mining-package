/*
 * Copyright (C) 2008-2011 by Holger Arndt
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

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.jdmp.gui.variable.VariableGUIObject;
import org.ujmp.core.calculation.Calculation.Ret;

public class FillWithValueAction extends VariableAction {
	private static final long serialVersionUID = -3609906043755395315L;

	public FillWithValueAction(JComponent c, VariableGUIObject v) {
		super(c, v);
		putValue(Action.NAME, "Fill With Value...");
		putValue(Action.SHORT_DESCRIPTION, "Fills all values with specified value");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_V,
				InputEvent.ALT_DOWN_MASK));
	}

	
	public Object call() {
		double value = 0;
		String s = JOptionPane.showInputDialog("Enter value:");
		try {
			value = Double.parseDouble(s);
			getVariable().getCoreObject().addMatrix(
					getVariable().getCoreObject().getMatrix().fill(Ret.NEW, value));
		} catch (Exception ex) {
		}
		return null;
	}

}
