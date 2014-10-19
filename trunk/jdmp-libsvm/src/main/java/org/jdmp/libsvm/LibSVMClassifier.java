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

package org.jdmp.libsvm;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_problem;

import org.jdmp.core.algorithm.classification.AbstractClassifier;
import org.jdmp.core.algorithm.classification.Classifier;
import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.sample.Sample;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.util.MathUtil;

public class LibSVMClassifier extends AbstractClassifier {
	private static final long serialVersionUID = -3809157647628200950L;

	private final svm_parameter param = new svm_parameter();

	private svm_problem prob = null;

	private svm_model model = null;

	private final SVMType svmType;

	private final Kernel kernel;

	private double cost = 1.0;

	private int featureCount = 0;
	private int targetCount = 0;

	public enum Kernel {
		LINEAR, RBF, SIGMOID, POLYNOM
	};

	public enum SVMType {
		C_SVC, NU_SVC, ONE_CLASS, EPSILON_SVR, NU_SVR;
	};

	public LibSVMClassifier() {
		this(Kernel.RBF);
	}

	public LibSVMClassifier(Kernel kernel) {
		this(SVMType.C_SVC, Kernel.RBF);
	}

	public LibSVMClassifier(SVMType svmType, Kernel kernel) {
		super();
		setLabel("SVM " + kernel.name());
		this.svmType = svmType;
		this.kernel = kernel;
		createAlgorithm();
	}

	public void setCost(double cost) {
		this.cost = cost;
		param.C = cost;
	}

	private void createAlgorithm() {
		switch (svmType) {
		case C_SVC:
			param.svm_type = svm_parameter.C_SVC;
			break;
		case NU_SVC:
			param.svm_type = svm_parameter.NU_SVC;
			break;
		case ONE_CLASS:
			param.svm_type = svm_parameter.ONE_CLASS;
			break;
		case EPSILON_SVR:
			param.svm_type = svm_parameter.EPSILON_SVR;
			break;
		case NU_SVR:
			param.svm_type = svm_parameter.NU_SVR;
			break;
		}

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
		param.cache_size = 1000;
		param.C = cost;
		param.eps = 1e-3;
		param.p = 0.1;
		param.shrinking = 1;
		param.probability = 0;
		param.nr_weight = 0;
		param.weight_label = new int[0];
		param.weight = new double[0];
	}

	public void train(DataSet dataSet) {
		featureCount = getFeatureCount(dataSet);
		targetCount = getClassCount(dataSet);

		System.out.println("training started");

		prob = new svm_problem();
		prob.l = dataSet.getSampleMap().getSize();

		prob.x = new svm_node[prob.l][featureCount + 1];
		prob.y = new double[prob.l];

		int i = 0;
		for (Sample s : dataSet.getSampleMap()) {
			Matrix input = s.getMatrix(getInputLabel()).toColumnVector(Ret.NEW);
			if (svmType == SVMType.EPSILON_SVR || svmType == SVMType.NU_SVR) {
				prob.y[i] = s.getMatrix(getTargetLabel()).toRowVector(Ret.NEW).getAsDouble(0, 0);
			} else {
				int targetClass = (int) s.getMatrix(getTargetLabel()).toRowVector(Ret.NEW)
						.getCoordinatesOfMaximum()[ROW];
				prob.y[i] = targetClass;
			}
			prob.x[i][0] = new svm_node();
			prob.x[i][0].index = 0;
			prob.x[i][0].value = 1;
			for (int j = 0; j < featureCount; j++) {
				prob.x[i][j + 1] = new svm_node();
				prob.x[i][j + 1].index = j + 1;
				prob.x[i][j + 1].value = input.getAsDouble(0, j);
			}
			i++;
		}

		System.out.println("dataset converted");

		if (param.gamma == 0) {
			param.gamma = 1.0 / featureCount;
		}

		if (param.kernel_type == svm_parameter.PRECOMPUTED)
			for (i = 0; i < prob.l; i++) {
				if (prob.x[i][0].index != 0) {
					System.err
							.print("Wrong kernel matrix: first column must be 0:sample_serial_number\n");
					System.exit(1);
				}
				if ((int) prob.x[i][0].value <= 0 || (int) prob.x[i][0].value > featureCount) {
					System.err.print("Wrong input format: sample_serial_number out of range\n");
					System.exit(1);
				}
			}

		String error_msg = svm.svm_check_parameter(prob, param);

		System.out.println("training svm");

		if (error_msg != null) {
			System.err.print("Error: " + error_msg + "\n");
		} else {
			model = svm.svm_train(prob, param);
		}

		System.out.println("training finished");
	}

	public Matrix predict(Matrix input, Matrix weight) {
		input = input.toRowVector(Ret.NEW);

		if (svmType == SVMType.EPSILON_SVR || svmType == SVMType.NU_SVR) {

			int nr_class = svm.svm_get_nr_class(model);
			double[] prediction = new double[nr_class];

			int m = (int) input.getRowCount();
			svm_node[] x = new svm_node[m + 1];
			x[0] = new svm_node();
			x[0].index = 0;
			x[0].value = 1;
			for (int j = 0; j < m; j++) {
				x[j + 1] = new svm_node();
				x[j + 1].index = j + 1;
				x[j + 1].value = input.getAsDouble(j, 0);
			}

			svm.svm_predict_values(model, x, prediction);
			Matrix output = Matrix.Factory.linkToValue(prediction[0]);
			return output;
		} else {
			int nr_class = svm.svm_get_nr_class(model);
			double[] prediction = new double[nr_class];

			int m = (int) input.getRowCount();
			svm_node[] x = new svm_node[m + 1];
			x[0] = new svm_node();
			x[0].index = 0;
			x[0].value = 1;
			for (int j = 0; j < m; j++) {
				x[j + 1] = new svm_node();
				x[j + 1].index = j + 1;
				x[j + 1].value = input.getAsDouble(j, 0);
			}

			svm.svm_predict_probability(model, x, prediction);
			int[] label = new int[svm.svm_get_nr_class(model)];
			svm.svm_get_labels(model, label);
			Matrix output = Matrix.Factory.zeros(1, MathUtil.max(label) + 1);

			for (int i = 0; i < label.length; i++) {
				output.setAsDouble(prediction[i], 0, label[i]);
			}

			return output;
		}
	}

	public void train(Matrix input, Matrix sampleWeight, Matrix targetOutput) {
		throw new RuntimeException("not supported");
	}

	public void reset() {
		prob = null;
		model = null;
	}

	public Classifier emptyCopy() {
		LibSVMClassifier svm = new LibSVMClassifier(svmType, kernel);
		svm.setCost(cost);
		return svm;
	}

	public double getCost() {
		return cost;
	}

}
