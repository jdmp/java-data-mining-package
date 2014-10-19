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

package org.jdmp.liblinear;

import org.jdmp.core.algorithm.classification.AbstractClassifier;
import org.jdmp.core.algorithm.classification.Classifier;
import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.sample.Sample;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.util.MathUtil;

import de.bwaldvogel.liblinear.FeatureNode;
import de.bwaldvogel.liblinear.Linear;
import de.bwaldvogel.liblinear.Model;
import de.bwaldvogel.liblinear.Parameter;
import de.bwaldvogel.liblinear.Problem;
import de.bwaldvogel.liblinear.SolverType;

public class LibLinearClassifier extends AbstractClassifier {
	private static final long serialVersionUID = 895205125219258509L;

	private Parameter param = null;

	private Problem prob = null;

	private Model model = null;

	private final double bias = 1.0;

	public LibLinearClassifier() {
		this(new Parameter(SolverType.L2R_L2LOSS_SVC_DUAL, 1, 0.1));
	}

	public LibLinearClassifier(Parameter parameter) {
		this.param = parameter;
	}

	public Matrix predict(Matrix input, Matrix sampleWeight) {
		input = input.toRowVector(Ret.NEW);
		long rowCount = input.getRowCount();
		int count = 0;
		for (int j = 0; j < rowCount; j++) {
			double value = input.getAsDouble(j, 0);
			if (value != 0.0 && !MathUtil.isNaNOrInfinite(value)) {
				count++;
			}
		}
		// +1 to include bias
		FeatureNode[] x = new FeatureNode[count + 1];
		x[0] = new FeatureNode(1, bias);
		count = 0;
		for (int j = 0; j < rowCount; j++) {
			double value = input.getAsDouble(j, 0);
			if (value != 0.0 && !MathUtil.isNaNOrInfinite(value)) {
				x[count + 1] = new FeatureNode(j + 2, value);
				count++;
			}
		}

		Matrix result = null;
		if (model.isProbabilityModel()) {
			double[] probabilities = new double[model.getNrClass()];
			Linear.predictProbability(model, x, probabilities);
			result = Matrix.Factory.zeros(model.getNrClass(), 1);
			for (int i = 0; i < probabilities.length; i++) {
				int label = model.getLabels()[i];
				result.setAsDouble(probabilities[i], label, 0);
			}
		} else {
			double classId = Linear.predict(model, x);
			result = Matrix.Factory.zeros(Math.max(model.getNrClass(), (int) (classId + 1)), 1);
			result.setAsDouble(1.0, (int) classId, 0);
		}

		return result;
	}

	public void train(DataSet dataSet) {
		createAlgorithm();
		prob = new Problem();
		prob.l = dataSet.getSampleMap().getSize();
		// +1 for bias
		prob.n = getFeatureCount(dataSet) + 1;

		prob.x = new FeatureNode[prob.l][];
		prob.y = new double[prob.l];

		long time = System.currentTimeMillis();

		int i = 0;
		for (Sample s : dataSet.getSampleMap()) {
			if (System.currentTimeMillis() - time > 5000) {
				time = System.currentTimeMillis();
				System.out.println("Converting samples: "
						+ Math.round(((double) i / dataSet.getSampleMap().size() * 100)) + "% done");
			}
			Matrix input = s.getMatrix(getInputLabel()).toRowVector(Ret.NEW);
			int targetClass = (int) s.getMatrix(getTargetLabel()).toRowVector(Ret.NEW).getCoordinatesOfMaximum()[ROW];
			prob.y[i] = targetClass;
			long rowCount = input.getRowCount();
			int count = 0;
			for (int j = 0; j < rowCount; j++) {
				double value = input.getAsDouble(j, 0);
				if (value != 0.0 && !MathUtil.isNaNOrInfinite(value)) {
					count++;
				}
			}
			// +1 to include bias
			prob.x[i] = new FeatureNode[count + 1];
			prob.x[i][0] = new FeatureNode(1, bias);
			count = 0;
			for (int j = 0; j < rowCount; j++) {
				double value = input.getAsDouble(j, 0);
				if (value != 0.0 && !MathUtil.isNaNOrInfinite(value)) {
					prob.x[i][count + 1] = new FeatureNode(j + 2, value);
					count++;
				}
			}
			i++;
		}
		model = Linear.train(prob, param);
	}

	public void train(Matrix input, Matrix sampleWeight, Matrix targetOutput) {
		throw new RuntimeException("not supported");
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

}
