/*
 * Copyright (C) 2008-2016 by Holger Arndt
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

package org.jdmp.liblinear;

import org.jdmp.core.algorithm.classification.AbstractClassifier;
import org.jdmp.core.algorithm.classification.Classifier;
import org.jdmp.core.dataset.ListDataSet;
import org.jdmp.core.sample.Sample;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.util.MathUtil;

import de.bwaldvogel.liblinear.Feature;
import de.bwaldvogel.liblinear.Linear;
import de.bwaldvogel.liblinear.Model;
import de.bwaldvogel.liblinear.Parameter;
import de.bwaldvogel.liblinear.Problem;
import de.bwaldvogel.liblinear.SolverType;

public class LibLinearClassifier extends AbstractClassifier {
	private static final long serialVersionUID = 895205125219258509L;

	private final Parameter param;

	private Problem prob = null;

	private Model model = null;

	private final FeatureStore features;

	public LibLinearClassifier() {
		this(new Parameter(SolverType.L2R_LR, 1, 0.1));
	}

	public LibLinearClassifier(Parameter parameter) {
		this.param = parameter;
		this.features = new FeatureStore();
	}

	public void predictOne(Sample sample) {
		Matrix input = sample.getAsMatrix(INPUT);
		input = input.toColumnVector(Ret.LINK);
		long columnCount = input.getColumnCount();
		int count = 0;
		for (int j = 0; j < columnCount; j++) {
			double value = input.getAsDouble(0, j);
			if (value != 0.0 && !MathUtil.isNaNOrInfinite(value)) {
				count++;
			}
		}

		Feature[] x = new Feature[count];
		count = 0;
		for (int j = 0; j < columnCount; j++) {
			double value = input.getAsDouble(0, j);
			if (value != 0.0 && !MathUtil.isNaNOrInfinite(value)) {
				x[count] = features.get(j + 1, value);
				count++;
			}
		}

		sample.put(PREDICTED, predictOne(x));
	}

	public void trainAll(Problem problem) {
		this.prob = problem;
		model = Linear.train(prob, param);
	}

	public void trainAll(ListDataSet dataSet) {
		System.out.println("training started");
		createAlgorithm();
		prob = new Problem();
		prob.l = dataSet.size();
		prob.n = getFeatureCount(dataSet);
		prob.x = new Feature[prob.l][];
		prob.y = new double[prob.l];
		prob.bias = 1;

		long time = System.currentTimeMillis();

		int i = 0;
		for (Sample s : dataSet) {
			if (System.currentTimeMillis() - time > 5000) {
				time = System.currentTimeMillis();
				System.out.println("Converting samples: " + Math.round(((double) i / dataSet.size() * 100)) + "% done");
			}
			Matrix input = s.getAsMatrix(getInputLabel()).toColumnVector(Ret.LINK);
			int targetClass = (int) s.getAsMatrix(getTargetLabel()).toColumnVector(Ret.LINK)
					.getCoordinatesOfMaximum()[COLUMN];
			prob.y[i] = targetClass;
			long columnCount = input.getColumnCount();
			int count = 0;
			for (int j = 0; j < columnCount; j++) {
				double value = input.getAsDouble(0, j);
				if (value != 0.0 && !MathUtil.isNaNOrInfinite(value)) {
					count++;
				}
			}
			prob.x[i] = new Feature[count];
			count = 0;
			for (int j = 0; j < columnCount; j++) {
				double value = input.getAsDouble(0, j);
				if (value != 0.0 && !MathUtil.isNaNOrInfinite(value)) {
					prob.x[i][count] = features.get(j + 1, value);
					count++;
				}
			}
			i++;
		}
		model = Linear.train(prob, param);
	}

	public Classifier emptyCopy() {
		LibLinearClassifier ll = new LibLinearClassifier(param);
		ll.setInputLabel(getInputLabel());
		ll.setTargetLabel(getTargetLabel());
		return ll;
	}

	private void createAlgorithm() {
		model = null;
		prob = null;
	}

	public void reset() {
		createAlgorithm();
	}

	public Matrix predictOne(Feature[] x) {
		Matrix result = null;
		if (model.isProbabilityModel()) {
			double[] probabilities = new double[model.getNrClass()];
			Linear.predictProbability(model, x, probabilities);
			result = Matrix.Factory.zeros(1, model.getNrClass());
			for (int i = 0; i < probabilities.length; i++) {
				int label = model.getLabels()[i];
				result.setAsDouble(probabilities[i], 0, label);
			}
		} else {
			double classId = Linear.predict(model, x);
			result = Matrix.Factory.zeros(1, Math.max(model.getNrClass(), (int) (classId + 1)));
			result.setAsDouble(1.0, 0, (int) classId);
		}

		return result;
	}

}
