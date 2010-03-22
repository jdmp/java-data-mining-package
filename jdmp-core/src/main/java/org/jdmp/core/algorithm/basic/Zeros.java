/*
 * Copyright (C) 2008-2010 by Holger Arndt
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

import org.jdmp.core.algorithm.AlgorithmTwoSources;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.MathUtil;

public class Zeros extends AlgorithmTwoSources {
	private static final long serialVersionUID = -6698288195664853309L;

	public static final String DESCRIPTION = "returns a matrix filled with zeros";

	public Zeros(Variable... variables) {
		super(variables);
	}

	public Map<String, Object> calculateObjects(Map<String, Object> input) throws MatrixException {
		Map<String, Object> result = new HashMap<String, Object>();

		Matrix source1 = MathUtil.getMatrix(input.get(SOURCE1));
		Matrix source2 = MathUtil.getMatrix(input.get(SOURCE2));

		result.put(TARGET, Matrix.factory.create((long) source1.doubleValue(), (long) source2
				.doubleValue()));

		return result;

	}
}
