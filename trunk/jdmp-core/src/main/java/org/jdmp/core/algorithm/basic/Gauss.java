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

package org.jdmp.core.algorithm.basic;

import java.util.HashMap;
import java.util.Map;

import org.jdmp.core.algorithm.AbstractAlgorithm;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.MathUtil;

public class Gauss extends AbstractAlgorithm {
	private static final long serialVersionUID = 3127916742763698423L;

	public static final String DESCRIPTION = "target = gauss(source)";

	private final double sigma = 1;

	private final double mu = 0.0;

	public Gauss(Variable... variables) {
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

	public Map<String, Object> calculateObjects(Map<String, Object> input) throws MatrixException {
		Map<String, Object> result = new HashMap<String, Object>();

		Matrix in = Matrix.Factory.copyFromMatrix(MathUtil.getMatrix(input.get(SOURCE)));
		for (long[] c : in.allCoordinates()) {
			in.setAsDouble(getProbability(in.getAsDouble(c)), c);
		}

		result.put(TARGET, in);
		return result;
	}

	public double getProbability(double x) {
		return 1.0 / (sigma * Math.sqrt(2 * Math.PI))
				* Math.exp(-Math.pow(x - mu, 2) / (2 * sigma * sigma));
	}

	public void setSourceVariable(Variable source) {
		setVariable(SOURCE, source);
	}

	public void setTargetVariable(Variable target) {
		setVariable(TARGET, target);
	}

}
