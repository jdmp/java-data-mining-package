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

package org.jdmp.core.algorithm.classification.mlp;

import java.util.HashMap;
import java.util.Map;

import org.jdmp.core.algorithm.AlgorithmTwoSources;
import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.MathUtil;

public class ReturningFunctionTanh extends AlgorithmTwoSources {
	private static final long serialVersionUID = -6450772877472534795L;

	public ReturningFunctionTanh() {
		setDescription("target = (1-output^2) * outputdeviation");
	}

	@Override
	public Map<Object, Object> calculateObjects(Map<Object, Object> input) throws MatrixException {
		Map<Object, Object> result = new HashMap<Object, Object>();

		Matrix output = MathUtil.getMatrix(input.get(SOURCE1));
		Matrix outputdeviation = MathUtil.getMatrix(input.get(SOURCE2));

		Matrix product = output.times(output);
		Matrix factor1 = product.times(-1).plus(1);
		Matrix target = factor1.times(outputdeviation);

		result.put(TARGET, target);

		return result;
	}

}
