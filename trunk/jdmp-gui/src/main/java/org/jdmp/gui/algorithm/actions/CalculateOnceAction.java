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

package org.jdmp.gui.algorithm.actions;

import javax.swing.Action;
import javax.swing.JComponent;

import org.jdmp.gui.algorithm.AlgorithmGUIObject;
import org.ujmp.core.Matrix;

public class CalculateOnceAction extends AlgorithmAction {
	private static final long serialVersionUID = 2007146067856422375L;

	private Matrix matrix = null;

	public CalculateOnceAction(JComponent c, AlgorithmGUIObject a, Matrix m) {
		super(c, a);
		this.matrix = m;
		putValue(Action.NAME, a.getLabel());
		putValue(Action.SHORT_DESCRIPTION, "Executes this algorithm once");
	}

	public CalculateOnceAction(JComponent c, AlgorithmGUIObject a) {
		super(c, a);
		putValue(Action.NAME, "Calculate once");
		putValue(Action.SHORT_DESCRIPTION, "Executes this algorithm once");
	}

	
	public Object call() {
		try {
			if (matrix == null) {
				getAlgorithm().getCoreObject().calculate();
			} else {
				getAlgorithm().getCoreObject().calculate(matrix);
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
