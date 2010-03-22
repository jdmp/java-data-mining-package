/*
 * Copyright (C) 2008-2010 by Holger Arndt
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

import java.util.Map;

import org.jdmp.core.algorithm.AbstractAlgorithm;
import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.algorithm.basic.FMeasure;
import org.jdmp.core.algorithm.basic.Minus;
import org.jdmp.core.algorithm.basic.Precision;
import org.jdmp.core.algorithm.basic.Recall;
import org.jdmp.core.algorithm.basic.Sensitivity;
import org.jdmp.core.algorithm.basic.Specificity;
import org.jdmp.core.dataset.ClassificationDataSet;
import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.util.MathUtil;

public abstract class AbstractRegressor extends AbstractAlgorithm implements Regressor {
	private static final long serialVersionUID = 4674447558395794134L;

	public static final String OUTPUTERRORALGORITHM = "OutputErrorAlgorithm";

	public static final int TRAIN = 0;

	public static final int PREDICT = 1;

	public int mode = TRAIN;

	private boolean evaluate = true;

	public AbstractRegressor() {
		super();
		setAlgorithm(OUTPUTERRORALGORITHM, new Minus());
	}

	public AbstractRegressor(boolean evaluate) {
		this();
		this.evaluate = evaluate;
	}

	public abstract void train(RegressionDataSet dataSet) throws Exception;

	public abstract void reset() throws Exception;

	public void train(Matrix input, Matrix sampleWeight, Matrix targetOutput) throws Exception {
		throw new Exception("not supported");
	}

	public final void predict(Sample sample) throws Exception {
		Matrix predicted = predict(sample.getVariables().getMatrix(INPUT), sample.getVariables()
				.getMatrix(WEIGHT));
		sample.getVariables().setMatrix(PREDICTED, predicted);
		if (evaluate) {
			Map<String, Object> result = getOutputErrorAlgorithm().calculate(predicted,
					sample.getVariables().getMatrix(TARGET));
			Matrix error = MathUtil.getMatrix(result.get(TARGET));
			sample.getVariables().setMatrix(DIFFERENCE, error);
			sample.getVariables().setMatrix(RMSE, MatrixFactory.linkToValue(error.getRMS()));
		}
		sample.notifyGUIObject();
	}

	public final Matrix predict(Matrix input) throws Exception {
		return predict(input.toRowVector(), null);
	}

	public final void train(Matrix input, Matrix targetOutput) throws Exception {
		train(input, MatrixFactory.linkToValue(1.0), targetOutput);
	}

	public abstract Matrix predict(Matrix input, Matrix sampleWeight) throws Exception;

	public final void train(Sample sample) throws Exception {
		Matrix input = sample.getVariables().getMatrix(INPUT);
		Matrix weight = sample.getVariables().getMatrix(WEIGHT);
		Matrix target = sample.getVariables().getMatrix(TARGET);
		train(input, weight, target);
	}

	public void predict(RegressionDataSet dataSet) throws Exception {

		Matrix confusion = null;
		double error = 0.0;
		int correctCount = 0;
		int errorCount = 0;
		int classCount = 0;

		if (dataSet instanceof ClassificationDataSet) {
			classCount = ((ClassificationDataSet) dataSet).getClassCount();
			confusion = Matrix.factory.create(classCount, classCount);
			confusion.setAxisAnnotation(Matrix.ROW, "expected");
			confusion.setAxisAnnotation(Matrix.COLUMN, "predicted");
		}

		for (Sample sample : dataSet.getSamples()) {

			predict(sample);

			if (evaluate) {
				double rmse = sample.getVariables().getMatrix(RMSE).getEuklideanValue();
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

		Matrix rmse = MatrixFactory.linkToValue(Math.sqrt(error / dataSet.getSamples().getSize()));
		rmse.setLabel("RMSE with " + getLabel());
		dataSet.getVariables().setMatrix(Variable.RMSE, rmse);

		if ((dataSet instanceof ClassificationDataSet)) {
			confusion.setLabel("Confusion with " + getLabel());
			dataSet.getVariables().setMatrix(Variable.CONFUSION, confusion);

			Matrix accuracy = MatrixFactory.linkToValue((double) correctCount
					/ (double) dataSet.getSamples().getSize());
			accuracy.setLabel("Accuracy with " + getLabel());
			dataSet.getVariables().setMatrix(Variable.ACCURACY, accuracy);

			Matrix errorMatrix = MatrixFactory.linkToValue(errorCount);
			errorMatrix.setLabel("Errors with " + getLabel());
			dataSet.getVariables().setMatrix(Variable.ERRORCOUNT, errorMatrix);

			Matrix sensitivity = (Matrix) new Sensitivity().calculate(confusion).get(TARGET);
			dataSet.getVariables().setMatrix(Variable.SENSITIVITY, sensitivity);

			Matrix specificity = (Matrix) new Specificity().calculate(confusion).get(TARGET);
			dataSet.getVariables().setMatrix(Variable.SPECIFICITY, specificity);

			Matrix precision = (Matrix) new Precision().calculate(confusion).get(TARGET);
			dataSet.getVariables().setMatrix(Variable.PRECISION, precision);

			Matrix recall = (Matrix) new Recall().calculate(confusion).get(TARGET);
			dataSet.getVariables().setMatrix(Variable.RECALL, recall);

			Matrix fmeasure = (Matrix) new FMeasure().calculate(confusion).get(TARGET);
			dataSet.getVariables().setMatrix(Variable.FMEASURE, fmeasure);

			Matrix fmeasureMacro = fmeasure.mean(Ret.NEW, Matrix.ALL, false);
			dataSet.getVariables().setMatrix(Variable.FMEASUREMACRO, fmeasureMacro);
		}

		dataSet.notifyGUIObject();
	}

	public Algorithm getOutputErrorAlgorithm() {
		return getAlgorithms().get(OUTPUTERRORALGORITHM);
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
