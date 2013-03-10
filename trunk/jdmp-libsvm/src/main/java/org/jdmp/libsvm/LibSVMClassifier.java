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

package org.jdmp.libsvm;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_problem;

import org.jdmp.core.algorithm.classification.AbstractClassifier;
import org.jdmp.core.algorithm.classification.Classifier;
import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.core.sample.Sample;
import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.MathUtil;

public class LibSVMClassifier extends AbstractClassifier {
	private static final long serialVersionUID = -3809157647628200950L;

	private svm_parameter param = null;

	private svm_problem prob = null; // set by read_problem

	private svm_model model = null;

	private Kernel kernel = null;

	public enum Kernel {
		LINEAR, RBF, SIGMOID, POLYNOM
	};

	public LibSVMClassifier() {
		this(Kernel.RBF);
	}

	public LibSVMClassifier(Kernel kernel) {
		super();
		setLabel("SVM " + kernel.name());
		this.kernel = kernel;
		createAlgorithm();
	}

	private void createAlgorithm() {
		param = new svm_parameter();

		param.svm_type = svm_parameter.C_SVC;

		switch (kernel) {
		case LINEAR:
			param.kernel_type = svm_parameter.LINEAR;
			break;
		case SIGMOID:
			param.kernel_type = svm_parameter.SIGMOID;
			break;
		case POLYNOM:
			param.kernel_type = svm_parameter.POLY;
			break;
		case RBF:
			param.kernel_type = svm_parameter.RBF;
			break;
		}

		param.degree = 3;
		param.gamma = 0; // 1/k
		param.coef0 = 0;
		param.nu = 0.5;
		param.cache_size = 100;
		param.C = 1;
		param.eps = 1e-3;
		param.p = 0.1;
		param.shrinking = 1;
		param.probability = 1;
		param.nr_weight = 0;
		param.weight_label = new int[0];
		param.weight = new double[0];
	}

	public void train(RegressionDataSet dataSet) throws MatrixException {
		int columnCount = (int) dataSet.getSamples().iterator().next().getVariables().getMatrix(
				INPUT).getColumnCount();

		prob = new svm_problem();
		prob.l = dataSet.getSamples().getSize();

		prob.x = new svm_node[prob.l][columnCount];
		prob.y = new double[prob.l];

		int i = 0;
		for (Sample p : dataSet.getSamples()) {
			Matrix input = p.getVariables().getMatrix(INPUT);
			prob.y[i] = p.getTargetClass();
			for (int j = 0; j < columnCount; j++) {
				prob.x[i][j] = new svm_node();
				prob.x[i][j].index = j + 1;
				prob.x[i][j].value = input.getAsDouble(0, j);
			}
			i++;
		}

		if (param.gamma == 0)
			param.gamma = 1.0 / columnCount;

		if (param.kernel_type == svm_parameter.PRECOMPUTED)
			for (i = 0; i < prob.l; i++) {
				if (prob.x[i][0].index != 0) {
					System.err
							.print("Wrong kernel matrix: first column must be 0:sample_serial_number\n");
					System.exit(1);
				}
				if ((int) prob.x[i][0].value <= 0 || (int) prob.x[i][0].value > columnCount) {
					System.err.print("Wrong input format: sample_serial_number out of range\n");
					System.exit(1);
				}
			}

		String error_msg = svm.svm_check_parameter(prob, param);

		if (error_msg != null) {
			System.err.print("Error: " + error_msg + "\n");
		} else {
			model = svm.svm_train(prob, param);
		}
	}

	public Matrix predict(Matrix input, Matrix weight) throws MatrixException {
		int nr_class = svm.svm_get_nr_class(model);
		double[] prob_estimates = new double[nr_class];

		int m = (int) input.getColumnCount();
		svm_node[] x = new svm_node[m];
		for (int j = 0; j < m; j++) {
			x[j] = new svm_node();
			x[j].index = j + 1;
			x[j].value = input.getAsDouble(0, j);
		}

		svm.svm_predict_probability(model, x, prob_estimates);
		int[] label = new int[svm.svm_get_nr_class(model)];
		svm.svm_get_labels(model, label);
		Matrix output = Matrix.Factory.zeros(1, MathUtil.max(label) + 1);

		for (int i = 0; i < label.length; i++) {
			output.setAsDouble(prob_estimates[i], 0, label[i]);
		}

		return output;
	}

	public void train(Matrix input, Matrix sampleWeight, Matrix targetOutput) throws Exception {
		throw new Exception("not supported");
	}

	public void reset() throws MatrixException {
		createAlgorithm();
	}

	public Classifier emptyCopy() {
		return new LibSVMClassifier(kernel);
	}

}
