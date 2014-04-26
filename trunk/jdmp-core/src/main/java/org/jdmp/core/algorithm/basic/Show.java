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

package org.jdmp.core.algorithm.basic;

import java.awt.Container;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import org.jdmp.core.algorithm.AbstractAlgorithm;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.interfaces.CoreObject;
import org.ujmp.core.interfaces.GUIObject;

public class Show extends AbstractAlgorithm {
	private static final long serialVersionUID = -5552405417194118742L;

	public static final String DESCRIPTION = "shows an Object in a JFrame";

	public Show(Variable... variables) {
		super();
		setDescription(DESCRIPTION);
		addVariableKey(SOURCE);
		addVariableKey(TARGET);
		setEdgeLabel(SOURCE, "Source");
		setEdgeLabel(TARGET, "Target");
		setEdgeDirection(SOURCE, EdgeDirection.Incoming);
		setEdgeDirection(TARGET, EdgeDirection.Outgoing);
		setVariables(variables);
	}

	public Map<String, Object> calculateObjects(Map<String, Object> input) {
		Map<String, Object> result = new HashMap<String, Object>();
		Object o = input.get(SOURCE);
		Object ret = null;
		if (o instanceof CoreObject) {
			ret = ((CoreObject) o).showGUI();
		} else if (o instanceof GUIObject) {
			ret = ((GUIObject) o).showGUI();
		} else if (o instanceof Matrix) {
			ret = ((Matrix) o).showGUI();
		} else if (o instanceof JFrame) {
			((JFrame) o).setVisible(true);
			ret = o;
		} else if (o instanceof Container) {
			JFrame frame = new JFrame();
			frame.setSize(1024, 768);
			frame.setContentPane((Container) o);
			frame.setVisible(true);
		}
		result.put(TARGET, ret);
		return result;
	}

}
