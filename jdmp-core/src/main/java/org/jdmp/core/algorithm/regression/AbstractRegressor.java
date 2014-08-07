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

import org.jdmp.core.algorithm.AbstractAlgorithm;
import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.algorithm.basic.FMeasure;
import org.jdmp.core.algorithm.basic.Minus;
import org.jdmp.core.algorithm.basic.Precision;
import org.jdmp.core.algorithm.basic.Recall;
import org.jdmp.core.algorithm.basic.Sensitivity;
import org.jdmp.core.algorithm.basic.Specificity;
import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;

public abstract class AbstractRegressor extends AbstractAlgorithm implements Regressor {
	private static final long serialVersionUID = 4674447558395794134L;

	public static final String OUTPUTERRORALGORITHM = "OutputErrorAlgorithm";

	public static final int TRAIN = 0;

	public static final int PREDICT = 1;

	public int mode = TRAIN;

	private boolean evaluate = true;

	private int iteration = 0;

	public AbstractRegressor() {
		super();
		setAlgorithm(OUTPUTERRORALGORITHM, new Minus());
	}

	public AbstractRegressor(boolean evaluate) {
		this();
		this.evaluate = evaluate;
	}

	public abstract void train(DataSet dataSet) throws Exception;

	public abstract void reset() throws Exception;

	public void train(Matrix input, Matrix sampleWeight, Matrix targetOutput) throws Exception {
		throw new Exception("not supported");
	}

	public final void predict(Sample sample) throws Exception {
		Matrix predicted = predict(sample.getMatrix(INPUT), sample.getMatrix(WEIGHT));
		sample.setMatrix(PREDICTED, predicted);
		if (evaluate) {
			Matrix target = sample.getMatrix(TARGET).toRowVector(Ret.NEW);
			Matrix error = target.minus(predicted);
			sample.setMatrix(DIFFERENCE, error);
			sample.setMatrix(RMSE, Matrix.Factory.linkToValue(error.getRMS()));
		}
		sample.fireValueChanged();
	}

	public final Matrix predict(Matrix input) throws Exception {
		return predict(input.toRowVector(Ret.NEW), null);
	}

	public final void train(Matrix input, Matrix targetOutput) throws Exception {
		train(input, Matrix.Factory.linkToValue(1.0), targetOutput);
	}

	public abstract Matrix predict(Matrix input, Matrix sampleWeight) throws Exception;

	public final void train(Sample sample) throws Exception {
		Matrix input = sample.getMatrix(INPUT);
		Matrix weight = sample.getMatrix(WEIGHT);
		Matrix target = sample.getMatrix(TARGET);
		train(input, weight, target);
	}

	public void predict(DataSet dataSet) throws Exception {
		Matrix confusion = null;
		double error = 0.0;
		int correctCount = 0;
		int errorCount = 0;
		int classCount = 0;

		classCount = dataSet.getClassCount();
		confusion = Matrix.Factory.zeros(classCount, classCount);
		confusion.setDimensionLabel(Matrix.ROW, "expected");
		confusion.setDimensionLabel(Matrix.COLUMN, "predicted");

		for (Sample sample : dataSet.getSampleMap()) {

			predict(sample);

			if (evaluate) {
				double rmse = sample.getMatrix(RMSE).getEuklideanValue();
				error += Math.pow(rmse, 2.0);

				int recognized = sample.getRecognizedClass();
				int targetClass = sample.getTargetClass();

				if (classCount == 1 || recognized == -1) {
					confusion.setAsDouble(confusion.getAsDouble(0, 0) + 1, 0, 0);
				} else {
					confusion.setAsDouble(confusion.getAsDouble(targetClass, recognized) + 1,
							targetClass, recognized);
				}

				if (sample.isCorrect()) {
					correctCount++;
					// weight = weight * 0.99;
				} else {
					errorCount++;
					// weight = weight / 0.99;
				}

				// ((RegressionSample) sample).setWeight(weight);

			}
		}

		Matrix rmse = Matrix.Factory.linkToValue(Math
				.sqrt(error / dataSet.getSampleMap().getSize()));
		rmse.setLabel("RMSE with " + getLabel());
		dataSet.getVariableMap().setMatrix(Variable.RMSE, rmse);

		double maxRMSE = 0.0;
		double minRMSE = 1e100;
		double sumRMSE = 0;
		for (Sample s : dataSet.getSampleMap()) {
			Matrix m = s.getMatrix("RMSE");
			if (m != null) {
				double sampleRMSE = m.doubleValue();
				sumRMSE += sampleRMSE;
				if (sampleRMSE > maxRMSE) {
					maxRMSE = sampleRMSE;
				}
				if (sampleRMSE < minRMSE) {
					minRMSE = sampleRMSE;
				}
			}
		}

		// calculate sample weight
		// if (maxRMSE > 0 && maxRMSE != minRMSE) {
		// for (Sample s : dataSet.getSampleMap()) {
		// Matrix m = s.getMatrix("RMSE");
		// if (m != null) {
		// double sampleWeight = (m.doubleValue() / (sumRMSE));
		// s.setMatrix("SampleWeight",
		// Matrix.Factory.linkToValue(sampleWeight));
		// }
		// }
		// }

		confusion.setLabel("Confusion with " + getLabel());
		dataSet.getVariableMap().setMatrix(Variable.CONFUSION, confusion);

		Matrix accuracy = Matrix.Factory.linkToValue((double) correctCount
				/ (double) dataSet.getSampleMap().getSize());
		accuracy.setLabel("Accuracy with " + getLabel());
		dataSet.getVariableMap().setMatrix(Variable.ACCURACY, accuracy);

		Matrix errorMatrix = Matrix.Factory.linkToValue(errorCount);
		errorMatrix.setLabel("Errors with " + getLabel());
		dataSet.getVariableMap().setMatrix(Variable.ERRORCOUNT, errorMatrix);

		Matrix sensitivity = (Matrix) new Sensitivity().calculate(confusion).get(TARGET);
		dataSet.getVariableMap().setMatrix(Variable.SENSITIVITY, sensitivity);

		Matrix specificity = (Matrix) new Specificity().calculate(confusion).get(TARGET);
		dataSet.getVariableMap().setMatrix(Variable.SPECIFICITY, specificity);

		Matrix precision = (Matrix) new Precision().calculate(confusion).get(TARGET);
		dataSet.getVariableMap().setMatrix(Variable.PRECISION, precision);

		Matrix recall = (Matrix) new Recall().calculate(confusion).get(TARGET);
		dataSet.getVariableMap().setMatrix(Variable.RECALL, recall);

		Matrix fmeasure = (Matrix) new FMeasure().calculate(confusion).get(TARGET);
		dataSet.getVariableMap().setMatrix(Variable.FMEASURE, fmeasure);

		Matrix fmeasureMacro = fmeasure.mean(Ret.NEW, Matrix.ALL, false);
		dataSet.getVariableMap().setMatrix(Variable.FMEASUREMACRO, fmeasureMacro);

		iteration++;
		System.out.println("Iteration: " + iteration + ", RMSE: " + rmse.doubleValue()
				+ ", errors: " + errorCount);

		dataSet.fireValueChanged();
	}

	public Algorithm getOutputErrorAlgorithm() {
		return getAlgorithmMap().get(OUTPUTERRORALGORITHM);
	}

	public void setOutputErrorAlgorithm(Algorithm a) {
		setAlgorithm(OUTPUTERRORALGORITHM, a);
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

}
