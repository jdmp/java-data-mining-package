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

package org.jdmp.core.algorithm.regression;

import org.jdmp.core.algorithm.classification.AbstractClassifier;
import org.jdmp.core.algorithm.classification.Classifier;
import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.core.variable.DefaultVariable;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;

/**
 * AlgorithmLinearRegression extends AlgorithmClassifier and not
 * AlgorithmRegression because also classification is possible using regression
 */
public class LinearRegression extends AbstractClassifier {
	private static final long serialVersionUID = 3483912497269476834L;

	public static final int PARAMETERS = 1;

	public LinearRegression() {
		super();
		setParameterVariable(new DefaultVariable("Regression Parameters"));
	}

	public void setParameterVariable(Variable variable) {
		setVariable(PARAMETERS, variable);
	}

	public Variable getParameterVariable() {
		return getVariableList().get(PARAMETERS);
	}

	public Matrix getParameterMatrix() {
		return getParameterVariable().getMatrix();
	}

	@Override
	public Matrix predict(Matrix input, Matrix sampleWeight) throws Exception {
		return input.addColumnWithOnes().mtimes(getParameterMatrix());
	}

	@Override
	public void train(Matrix input, Matrix sampleWeight, Matrix targetOutput) throws Exception {
		throw (new Exception("pattern-by-pattern learning not supported"));
	}

	@Override
	public void train(RegressionDataSet dataSet) throws Exception {
		Matrix x = dataSet.getInputMatrix().addColumnWithOnes();
		Matrix y = dataSet.getTargetMatrix();
		// Matrix parameters =
		// x.transpose().mtimes(x).pinv().mtimes(x.transpose()).mtimes(y);
		Matrix parameters = x.pinv().mtimes(y);
		setParameterMatrix(parameters);
	}

	public void setParameterMatrix(Matrix parameters) {
		setMatrix(PARAMETERS, parameters);
	}

	@Override
	public void reset() throws MatrixException {
		getParameterVariable().clear();
	}

	public Classifier emptyCopy() {
		return new LinearRegression();
	}

}
