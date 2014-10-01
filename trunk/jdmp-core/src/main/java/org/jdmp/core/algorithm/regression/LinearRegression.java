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

package org.jdmp.core.algorithm.regression;

import org.jdmp.core.algorithm.classification.AbstractClassifier;
import org.jdmp.core.algorithm.classification.Classifier;
import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.VariableFactory;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;

/**
 * AlgorithmLinearRegression extends AlgorithmClassifier and not
 * AlgorithmRegression because also classification is possible using regression
 */
public class LinearRegression extends AbstractClassifier {
	private static final long serialVersionUID = 3483912497269476834L;

	public static final String PARAMETERS = "Parameters";

	private final int projectionDimensions;

	public LinearRegression() {
		this(0);
	}

	public LinearRegression(int projectionDimensions) {
		super();
		// use random projection
		this.projectionDimensions = projectionDimensions;
		setParameterVariable(VariableFactory.labeledVariable("Regression Parameters"));
	}

	public void setParameterVariable(Variable variable) {
		setVariable(PARAMETERS, variable);
	}

	public Variable getParameterVariable() {
		return getVariableMap().get(PARAMETERS);
	}

	public Matrix getParameterMatrix() {
		return getParameterVariable().getLast();
	}

	public Matrix predict(Matrix input, Matrix sampleWeight) {
		input = input.toColumnVector(Ret.NEW);
		Matrix x = Matrix.Factory.zeros(1, input.getColumnCount() + 1);
		x.setAsDouble(1, 0, 0);
		for (int c = 0; c < input.getColumnCount(); c++) {
			x.setAsDouble(input.getAsDouble(0, c), 0, c + 1);
		}
		Matrix result = x.mtimes(getParameterMatrix());
		return result;
	}

	public void train(Matrix input, Matrix sampleWeight, Matrix targetOutput) {
		throw new RuntimeException("pattern-by-pattern learning not supported");
	}

	public void train(DataSet dataSet) {
		System.out.println("training started");

		Matrix x = Matrix.Factory
				.zeros(dataSet.getSampleMap().size(), getFeatureCount(dataSet) + 1);
		Matrix y = Matrix.Factory.zeros(dataSet.getSampleMap().size(), getClassCount(dataSet));

		int i = 0;
		for (Sample s : dataSet.getSampleMap().values()) {
			x.setAsDouble(1, i, 0);
			Matrix input = s.getMatrix(getInputLabel()).toColumnVector(Ret.NEW);
			for (int c = 0; c < input.getColumnCount(); c++) {
				x.setAsDouble(input.getAsDouble(0, c), i, c + 1);
			}
			Matrix target = s.getMatrix(getTargetLabel()).toColumnVector(Ret.NEW);
			for (int c = 0; c < target.getColumnCount(); c++) {
				y.setAsDouble(target.getAsDouble(0, c), i, c);
			}
			i++;
		}

		System.out.println("data loaded");

		// this depends on the number of samples and is therefore slower for
		// large data sets:
		// Matrix parameters = x.pinv().mtimes(y);

		// this depends on the number of features only:
		Matrix parameters = null;
		final Matrix xtranspose = x.transpose();
		if (projectionDimensions < 1) {
			parameters = xtranspose.mtimes(x).pinv().mtimes(xtranspose).mtimes(y);
		} else {
			parameters = xtranspose.mtimes(x).pinv(projectionDimensions).mtimes(xtranspose)
					.mtimes(y);
		}

		System.out.println("training finished");

		setParameterMatrix(parameters);
	}

	public void setParameterMatrix(Matrix parameters) {
		getVariableMap().setMatrix(PARAMETERS, parameters);
	}

	public void reset() {
		getParameterVariable().clear();
	}

	public Classifier emptyCopy() {
		return new LinearRegression();
	}

}
