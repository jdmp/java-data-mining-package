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

import org.jdmp.core.algorithm.AlgorithmFiveSources;
import org.jdmp.core.algorithm.classification.mlp.MultiLayerNetwork.BiasType;
import org.jdmp.core.variable.DefaultVariable;
import org.jdmp.core.variable.Variable;
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
		Variable eta = new DefaultVariable("eta", 1);
		Matrix m = MatrixFactory.linkToValue(0.001);
		eta.addMatrix(m);
		setVariable(ETA, eta);

		Variable sampleWeight = new DefaultVariable("Sample Weight", 1);
		Matrix s = MatrixFactory.linkToValue(1);
		sampleWeight.addMatrix(s);
		setVariable(SAMPLEWEIGHT, sampleWeight);
	}

	public void setLearningRate(double v) {
		Matrix m = MatrixFactory.linkToValue(v);
		setMatrix(ETA, m);
	}

	public double getLearningRate() {
		return getMatrixFromVariable(ETA).getEuklideanValue();
	}

	public void setSampleWeight(double v) {
		Matrix m = MatrixFactory.linkToValue(v);
		setMatrix(SAMPLEWEIGHT, m);
	}

	@Override
	public Map<Object, Matrix> calculate(Map<Object, Matrix> matrices) throws MatrixException {
		Map<Object, Matrix> result = new HashMap<Object, Matrix>();

		Matrix weight = matrices.get(WEIGHT);
		double eta = matrices.get(ETA).getEuklideanValue();
		Matrix contactDeviation = matrices.get(CONTACTDEVIATION);
		double sampleWeight = matrices.get(SAMPLEWEIGHT).getEuklideanValue();

		Matrix transposedInput = matrices.get(INPUT).toColumnVector();

		switch (biasType) {
		case SINGLE:
			transposedInput = transposedInput.addColumnWithOnes();
			break;
		case MULTIPLE:
			Matrix bias = MatrixFactory.ones(transposedInput.getSize());
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
		double missingValueCount = transposedInput.countMissing(Ret.NEW, ALL).getEuklideanValue();

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
