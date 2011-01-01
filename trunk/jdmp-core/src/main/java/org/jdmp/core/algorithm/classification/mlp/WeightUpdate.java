/*
 * Copyright (C) 2008-2011 by Holger Arndt
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

import org.jdmp.core.algorithm.AlgorithmFiveSources;
import org.jdmp.core.algorithm.classification.mlp.MultiLayerNetwork.BiasType;
import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.VariableFactory;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.MathUtil;

public class WeightUpdate extends AlgorithmFiveSources {
	private static final long serialVersionUID = -6437801270586210637L;

	public static final String WEIGHT = SOURCE1;

	public static final String ETA = SOURCE2;

	public static final String CONTACTDEVIATION = SOURCE3;

	public static final String INPUT = SOURCE4;

	public static final String SAMPLEWEIGHT = SOURCE5;

	private BiasType biasType = null;

	public WeightUpdate(BiasType biasType) {
		this.biasType = biasType;
		setDescription("weight = weight + eta * sampleweight * inputdeviation * input");
		Variable eta = VariableFactory.singleValue("eta", 1);
		Matrix m = MatrixFactory.linkToValue(0.001);
		eta.addMatrix(m);
		setVariable(ETA, eta);

		Variable sampleWeight = VariableFactory.singleValue("Sample Weight", 1);
		Matrix s = MatrixFactory.linkToValue(1);
		sampleWeight.addMatrix(s);
		setVariable(SAMPLEWEIGHT, sampleWeight);
	}

	public void setLearningRate(double v) {
		Matrix m = MatrixFactory.linkToValue(v);
		getVariables().setMatrix(ETA, m);
	}

	public double getLearningRate() {
		return getVariables().getMatrix(ETA).doubleValue();
	}

	public void setSampleWeight(double v) {
		Matrix m = MatrixFactory.linkToValue(v);
		getVariables().setMatrix(SAMPLEWEIGHT, m);
	}

	public Map<String, Object> calculateObjects(Map<String, Object> matrices)
			throws MatrixException {
		Map<String, Object> result = new HashMap<String, Object>();

		Matrix weight = MathUtil.getMatrix(matrices.get(WEIGHT));
		double eta = MathUtil.getMatrix(matrices.get(ETA)).doubleValue();
		Matrix contactDeviation = MathUtil.getMatrix(matrices.get(CONTACTDEVIATION));
		double sampleWeight = MathUtil.getMatrix(matrices.get(SAMPLEWEIGHT)).doubleValue();

		Matrix transposedInput = MathUtil.getMatrix(matrices.get(INPUT)).toColumnVector(Ret.NEW);

		switch (biasType) {
		case SINGLE:
			Matrix bias = MatrixFactory.ones(transposedInput.getRowCount(), 1);
			transposedInput = MatrixFactory.horCat(transposedInput, bias);
			break;
		case MULTIPLE:
			bias = MatrixFactory.ones(transposedInput.getSize());
			for (long[] c : transposedInput.allCoordinates()) {
				if (MathUtil.isNaNOrInfinite(transposedInput.getAsDouble(c))) {
					bias.setAsDouble(Double.NaN, c);
				}
			}
			transposedInput = MatrixFactory.horCat(transposedInput, bias);
			break;
		case NONE:
			break;
		}

		double totalValueCount = transposedInput.getValueCount();
		double missingValueCount = transposedInput.countMissing(Ret.NEW, ALL).doubleValue();

		double boost = 1.0;

		// if (useBoost) {
		// boost = 1.0 / (totalValueCount - missingValueCount);
		boost = totalValueCount / (totalValueCount - missingValueCount);
		// }

		Matrix product = contactDeviation.mtimes(transposedInput);
		Matrix weightChange = product.times(eta * sampleWeight * boost);
		Matrix newWeight = weight.minus(Ret.NEW, true, weightChange);

		result.put(TARGET, newWeight);

		return result;
	}

	public void setWeightVariable(Variable v) {
		setVariable(WEIGHT, v);
		setVariable(TARGET, v);
	}

	public void setInputVariable(Variable v) {
		setVariable(INPUT, v);
	}

	public void setContactDeviationVariable(Variable v) {
		setVariable(CONTACTDEVIATION, v);
	}

	public void setLayer(int nr) {
		setLabel("Weight Update (" + nr + ")");
	}
}
