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

package org.jdmp.core.algorithm.classification.mlp;

import java.util.HashMap;
import java.util.Map;

import org.jdmp.core.algorithm.AlgorithmTwoSources;
import org.jdmp.core.algorithm.classification.mlp.MultiLayerNetwork.BiasType;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.doublematrix.impl.ArrayDenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.MathUtil;

public class Weighting extends AlgorithmTwoSources {
	private static final long serialVersionUID = -7663880296216560217L;

	private BiasType biasType = null;

	public Weighting(BiasType biasType) {
		setDescription("target_{ji} = weight_{ji} * x_i");
		this.biasType = biasType;
	}

	public Map<String, Object> calculateObjects(Map<String, Object> input) throws MatrixException {
		Map<String, Object> result = new HashMap<String, Object>();

		Matrix weight = MathUtil.getMatrix(input.get(SOURCE1));
		Matrix x = MathUtil.getMatrix(input.get(SOURCE2)).toRowVector();

		switch (biasType) {
		case SINGLE:
			Matrix bias = MatrixFactory.ones(1, x.getColumnCount());
			x = MatrixFactory.vertCat(x, bias);
			break;
		case MULTIPLE:
			bias = MatrixFactory.ones(x.getSize());
			for (long[] c : x.allCoordinates()) {
				if (MathUtil.isNaNOrInfinite(x.getAsDouble(c))) {
					bias.setAsDouble(Double.NaN, c);
				}
			}
			x = MatrixFactory.horCat(x, bias);
			break;
		case NONE:
			break;
		}

		double[][] target = new double[(int) weight.getSize()[ROW]][(int) weight.getSize()[COLUMN]];

		double xv = 0.0;
		for (int r, c = (int) weight.getColumnCount(); --c >= 0;) {
			xv = x.getAsDouble(c, 0);
			for (r = (int) weight.getRowCount(); --r >= 0;) {
				target[r][c] = weight.getAsDouble(r, c) * xv;
			}
		}

		result.put(TARGET, new ArrayDenseDoubleMatrix2D(target));

		return result;
	}

}
