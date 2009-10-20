/*
 * Copyright (C) 2008-2009 by Holger Arndt
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

import java.util.HashMap;
import java.util.Map;

import org.jdmp.core.algorithm.AbstractAlgorithm;
import org.jdmp.core.algorithm.classification.mlp.MultiLayerNetwork;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.MathUtil;

public class CreateMLP extends AbstractAlgorithm {
	private static final long serialVersionUID = -5372885309350915607L;
	public static final String DESCRIPTION = "creates a MultiLayerPerceptron";

	public static final String HIDDEN1 = "Hidden1";
	public static final String HIDDEN2 = "Hidden2";
	public static final String HIDDEN3 = "Hidden3";

	public CreateMLP(Variable... variables) {
		super();
		setDescription(DESCRIPTION);
		addVariableKey(HIDDEN1);
		addVariableKey(HIDDEN2);
		addVariableKey(HIDDEN3);
		addVariableKey(TARGET);
		setEdgeLabel(HIDDEN1, "neurons in hidden layer 1");
		setEdgeLabel(HIDDEN2, "neurons in hidden layer 2");
		setEdgeLabel(HIDDEN3, "neurons in hidden layer 3");
		setEdgeLabel(TARGET, "Target");
		setEdgeDirection(HIDDEN1, EdgeDirection.Incoming);
		setEdgeDirection(HIDDEN2, EdgeDirection.Incoming);
		setEdgeDirection(HIDDEN3, EdgeDirection.Incoming);
		setEdgeDirection(TARGET, EdgeDirection.Outgoing);
	}

	
	public Map<String, Object> calculateObjects(Map<String, Object> input)
			throws MatrixException {
		Map<String, Object> result = new HashMap<String, Object>();

		Object h1 = input.get(HIDDEN1);
		Object h2 = input.get(HIDDEN2);
		Object h3 = input.get(HIDDEN3);

		int[] hidden = null;
		if (h3 != null) {
			hidden = new int[] { MathUtil.getInt(h1), MathUtil.getInt(h2), MathUtil.getInt(h3) };
		} else if (h2 != null) {
			hidden = new int[] { MathUtil.getInt(h1), MathUtil.getInt(h2) };
		} else if (h1 != null) {
			hidden = new int[] { MathUtil.getInt(h1) };
		} else {
			hidden = new int[] {};
		}

		result.put(TARGET, new MultiLayerNetwork(hidden));
		return result;
	}

}
