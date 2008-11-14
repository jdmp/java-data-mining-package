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

package org.jdmp.gui.variable;

import org.ujmp.core.Matrix;
import org.ujmp.gui.icon.MatrixIcon;

public class VariableIcon extends MatrixIcon {
	private static final long serialVersionUID = 67250050019574827L;

	private VariableGUIObject variable = null;

	public VariableIcon(VariableGUIObject variable) {
		super(variable.getVariable().getMatrix());
		this.variable = variable;
	}

	@Override
	public Matrix getMatrix() {
		return variable.getVariable().getMatrix();
	}

	public VariableGUIObject getVariable() {
		return variable;
	}

	public void setVariable(VariableGUIObject variable) {
		this.variable = variable;
	}

}
