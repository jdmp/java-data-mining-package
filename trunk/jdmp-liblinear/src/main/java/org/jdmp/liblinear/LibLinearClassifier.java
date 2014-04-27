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
import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.core.sample.Sample;
import org.ujmp.core.Matrix;
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

	public Matrix predict(Matrix input, Matrix sampleWeight) throws Exception {
		long columnCount = input.getColumnCount();
		int count = 0;
		for (int j = 0; j < columnCount; j++) {
			double value = input.getAsDouble(0, j);
			if (value != 0.0 && !MathUtil.isNaNOrInfinite(value)) {
				count++;
			}
		}
		// +1 to include bias
		FeatureNode[] x = new FeatureNode[count + 1];
		x[0] = new FeatureNode(1, bias);
		count = 0;
		for (int j = 0; j < columnCount; j++) {
			double value = input.getAsDouble(0, j);
			if (value != 0.0 && !MathUtil.isNaNOrInfinite(value)) {
				x[count + 1] = new FeatureNode(j + 2, value);
				count++;
			}
		}

		double classId = Linear.predict(model, x);
		Matrix ret = Matrix.Factory.zeros(1, Math.max(model.getNrClass(), (int) (classId + 1)));
		ret.setAsDouble(1.0, 0, (int) classId);
		return ret;
	}

	public void train(RegressionDataSet dataSet) throws Exception {
		createAlgorithm();
		prob = new Problem();
		prob.l = dataSet.getSampleMap().getSize();
		prob.n = (int) dataSet.getSampleMap().iterator().next().getMatrix(INPUT).getColumnCount() + 1; // +1
																										// for
																										// bias

		prob.x = new FeatureNode[prob.l][];
		prob.y = new double[prob.l];

		int i = 0;
		for (Sample p : dataSet.getSampleMap()) {
			if (i % 10 == 0) {
				System.out.println("Converting sample " + i);
			}
			Matrix input = p.getMatrix(INPUT);
			prob.y[i] = p.getTargetClass();
			long columnCount = input.getColumnCount();
			int count = 0;
			for (int j = 0; j < columnCount; j++) {
				double value = input.getAsDouble(0, j);
				if (value != 0.0 && !MathUtil.isNaNOrInfinite(value)) {
					count++;
				}
			}
			// +1 to include bias
			prob.x[i] = new FeatureNode[count + 1];
			prob.x[i][0] = new FeatureNode(1, bias);
			count = 0;
			for (int j = 0; j < columnCount; j++) {
				double value = input.getAsDouble(0, j);
				if (value != 0.0 && !MathUtil.isNaNOrInfinite(value)) {
					prob.x[i][count + 1] = new FeatureNode(j + 2, value);
					count++;
				}
			}
			i++;
		}
		model = Linear.train(prob, param);
	}

	public void train(Matrix input, Matrix sampleWeight, Matrix targetOutput) throws Exception {
		throw new Exception("not supported");
	}

	public Classifier emptyCopy() throws Exception {
		return new LibLinearClassifier();
	}

	private void createAlgorithm() {
		model = null;
		prob = null;
	}

	public void reset() {
		createAlgorithm();
	}

}
