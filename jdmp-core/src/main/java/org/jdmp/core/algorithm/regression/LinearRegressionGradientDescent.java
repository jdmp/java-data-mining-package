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

import java.util.List;

import org.jdmp.core.algorithm.classification.AbstractClassifier;
import org.jdmp.core.algorithm.classification.Classifier;
import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.VariableFactory;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.collections.list.FastArrayList;
import org.ujmp.core.util.MathUtil;

public class LinearRegressionGradientDescent extends AbstractClassifier {
	private static final long serialVersionUID = 9107143531298310638L;

	public static final String PARAMETERS = "Parameters";
	public static final String GRADIENT = "Gradient";

	private int batchSize = 1000;

	private double eta = 0.01;

	private int epochs = 100;

	private double minImprovement = 1e-6;

	//private double weightDecay = eta / 10.0;
	private double weightDecay = 0.0;

	public LinearRegressionGradientDescent() {
		super();
		setParameterVariable(VariableFactory.labeledVariable("Regression Parameters"));
		setParameterVariable(VariableFactory.labeledVariable("Gradient"));
	}

	public void setParameterVariable(Variable variable) {
		setVariable(PARAMETERS, variable);
	}

	public Variable getParameterVariable() {
		return getVariableMap().get(PARAMETERS);
	}

	public void setGradientVariable(Variable variable) {
		setVariable(GRADIENT, variable);
	}

	public Variable getGradientVariable() {
		return getVariableMap().get(GRADIENT);
	}

	public Matrix getParameterMatrix() {
		return getParameterVariable().getLast();
	}

	public Matrix getGradientMatrix() {
		return getGradientVariable().getLast();
	}

	public Matrix predict(Matrix input, Matrix sampleWeight) throws Exception {
		input = input.toColumnVector(Ret.NEW);
		Matrix bias = Matrix.Factory.ones(input.getRowCount(), 1);
		Matrix data = Matrix.Factory.horCat(bias, input);
		Matrix result = getParameterMatrix().transpose().mtimes(data.transpose());
		return result.transpose();
	}

	public void train(Matrix input, Matrix sampleWeight, Matrix targetOutput) throws Exception {
		throw (new Exception("pattern-by-pattern learning not supported"));
	}

	public void train(DataSet dataSet) throws Exception {

		// todo: normalize data

		double lastRmse = Double.MAX_VALUE;
		double averageImprovement = Double.NaN;

		Matrix parameters = Matrix.Factory.randn(dataSet.getFeatureCount() + 1,
				dataSet.getClassCount()).divide(dataSet.getFeatureCount() + 1);

		List<Matrix> inputs = new FastArrayList<Matrix>();
		List<Matrix> targets = new FastArrayList<Matrix>();

		for (int e = 0; e < ((double) dataSet.getSampleMap().size() / batchSize) * epochs; e++) {

			inputs.clear();
			targets.clear();

			for (int i = 0; i < batchSize; i++) {
				int randomIndex = MathUtil.nextInteger(dataSet.getSampleMap().size());
				Sample s = dataSet.getSampleMap().getElementAt(randomIndex);
				inputs.add(s.getMatrix(Sample.INPUT).toColumnVector(Ret.NEW));
				targets.add(s.getMatrix(Sample.TARGET).toColumnVector(Ret.NEW));
			}

			Matrix input = Matrix.Factory.vertCat(inputs);
			Matrix bias = Matrix.Factory.ones(input.getRowCount(), 1);
			Matrix x = Matrix.Factory.horCat(bias, input);
			x = x.transpose();

			Matrix target = Matrix.Factory.vertCat(targets);
			target = target.transpose();

			Matrix y = parameters.transpose().mtimes(x);

			Matrix diff = y.minus(target);
			Matrix squared = diff.power(Ret.NEW, 2);

			double rmse = Math.sqrt(squared.getValueSum() / squared.getValueCount());
			double improvement = Math.abs(lastRmse - rmse);
			if (MathUtil.isNaNOrInfinite(averageImprovement)) {
				averageImprovement = improvement;
			}
			averageImprovement = averageImprovement * 0.99 + 0.01 * improvement;

			System.out.println(rmse);

			Matrix gradient = x.mtimes(diff.transpose()).times(eta / batchSize);

			setGradientMatrix(gradient);

			parameters = parameters.minus(gradient);
			parameters = parameters.times(1.0 - weightDecay);

			if (averageImprovement < minImprovement) {
				break;
			}

			lastRmse = rmse;

		}

		setParameterMatrix(parameters);
	}

	public void setParameterMatrix(Matrix parameters) {
		getVariableMap().setMatrix(PARAMETERS, parameters);
	}

	public void setGradientMatrix(Matrix gradient) {
		getVariableMap().setMatrix(GRADIENT, gradient);
	}

	public void reset() {
		getParameterVariable().clear();
	}

	public Classifier emptyCopy() {
		return new LinearRegressionGradientDescent();
	}

}
