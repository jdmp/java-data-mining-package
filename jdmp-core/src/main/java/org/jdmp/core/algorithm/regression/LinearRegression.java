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

import java.util.ArrayList;
import java.util.List;

import org.jdmp.core.algorithm.classification.AbstractClassifier;
import org.jdmp.core.algorithm.classification.Classifier;
import org.jdmp.core.dataset.RegressionDataSet;
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

	public LinearRegression() {
		super();
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

	public Matrix predict(Matrix input, Matrix sampleWeight) throws Exception {
		input = input.toColumnVector(Ret.NEW);
		Matrix bias = Matrix.Factory.ones(input.getRowCount(), 1);
		return Matrix.Factory.horCat(input, bias).mtimes(getParameterMatrix());
	}

	public void train(Matrix input, Matrix sampleWeight, Matrix targetOutput) throws Exception {
		throw (new Exception("pattern-by-pattern learning not supported"));
	}

	public void train(RegressionDataSet dataSet) throws Exception {
		List<Matrix> inputs = new ArrayList<Matrix>();
		List<Matrix> targets = new ArrayList<Matrix>();
		for (Sample s : dataSet.getSampleMap().values()) {
			inputs.add(s.getMatrix(Sample.INPUT).toColumnVector(Ret.NEW));
			targets.add(s.getMatrix(Sample.TARGET).toColumnVector(Ret.NEW));
		}

		Matrix input = Matrix.Factory.vertCat(inputs);
		Matrix bias = Matrix.Factory.ones(input.getRowCount(), 1);
		Matrix x = Matrix.Factory.horCat(input, bias);
		Matrix y = Matrix.Factory.vertCat(targets);

		// this depends on the number of samples and is therefore slower for
		// large data sets:
		// Matrix parameters = x.pinv().mtimes(y);

		// this depends on the number of features only:
		Matrix parameters = x.transpose().mtimes(x).pinv().mtimes(x.transpose()).mtimes(y);

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
