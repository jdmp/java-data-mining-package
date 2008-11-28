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
import org.jdmp.core.algorithm.classification.mlp.MultiLayerNetwork.BiasType;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.exceptions.MatrixException;

public class DimmingFunction extends AlgorithmTwoSources {
	private static final long serialVersionUID = -1391123608571352340L;

	private BiasType biasType = null;

	public DimmingFunction(BiasType biasType) {
		setDescription("target = weight' * contactdeviation");
		this.biasType = biasType;
	}

	@Override
	public Map<Object, Matrix> calculate(Map<Object, Matrix> input) throws MatrixException {
		Map<Object, Matrix> result = new HashMap<Object, Matrix>();

		Matrix weight = input.get(SOURCE1);
		Matrix contactdeviation = input.get(SOURCE2);

		Matrix transposedWeight = weight.transpose();
		Matrix target = transposedWeight.mtimes(contactdeviation);

		// ignore bias
		switch (biasType) {
		case SINGLE:
			target = target.subMatrix(Ret.NEW, 0, 0, target.getRowCount() - 2, 0);
			break;
		case MULTIPLE:
			target = target.subMatrix(Ret.NEW, 0, 0, target.getRowCount() / 2 - 1, 0);
			break;
		case NONE:
			break;
		}

		result.put(TARGET, target);

		return result;
	}

}
