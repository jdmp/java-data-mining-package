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

package org.jdmp.gui.module.actions;

import javax.swing.Action;
import javax.swing.JComponent;

import org.jdmp.gui.module.ModuleGUIObject;
import org.ujmp.core.Matrix;

public class AppendMatrixAction extends ModuleAction {
	private static final long serialVersionUID = -4511306901854037104L;

	private Matrix matrix = null;

	public AppendMatrixAction(JComponent c, ModuleGUIObject module, Matrix m) {
		super(c, module);
		this.matrix = m;

		putValue(Action.NAME, "Set Matrix");
		putValue(Action.SHORT_DESCRIPTION, "Sets the Matrix to a specified value");
	}

	@Override
	public Object call() {
		// if (getModule() != null) {
		// getModule().appendMatrixForVariableReference(reference, matrix);
		// }
		return null;
	}

}
