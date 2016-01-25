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

package org.jdmp.gui.variable.actions;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.jdmp.gui.variable.VariableGUIObject;
import org.ujmp.core.calculation.Calculation.Ret;

public class FillGaussianAction extends VariableAction {
	private static final long serialVersionUID = 2702306358440290066L;

	public FillGaussianAction(JComponent c, VariableGUIObject v) {
		super(c, v);
		putValue(Action.NAME, "Fill Gaussian");
		putValue(Action.SHORT_DESCRIPTION, "Fills all values with gaussian noise");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_G);
		putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.ALT_DOWN_MASK));
	}

	public Object call() {
		getVariable().getCoreObject().add(getVariable().getCoreObject().getLast().randn(Ret.NEW));
		return null;
	}

}
