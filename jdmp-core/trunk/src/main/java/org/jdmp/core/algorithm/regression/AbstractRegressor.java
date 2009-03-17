/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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
import org.jdmp.core.algorithm.basic.Minus;
import org.jdmp.core.dataset.ClassificationDataSet;
import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.core.sample.ClassificationSample;
import org.jdmp.core.sample.Sample;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.util.MathUtil;

public abstract class AbstractRegressor extends AbstractAlgorithm implements Regressor {
	private static final long serialVersionUID = 4674447558395794134L;

	public static final int OUTPUTERRORALGORITHM = 0;

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

	public abstract void train(Matrix input, Matrix sampleWeight, Matrix targetOutput)
			throws Exception;

	public final void predict(Sample sample) throws Exception {
		Matrix predicted = predict(sample.getMatrix(INPUT), sample.getMatrix(WEIGHT));
		sample.setMatrix(PREDICTED, predicted);
		if (evaluate) {
			Matrix error = MathUtil.getMatrix(getOutputErrorAlgorithm().calculate(predicted,
					MathUtil.getMatrix(sample.getMatrix(TARGET))).values().iterator().next());
			sample.setMatrix(DIFFERENCE, error);
			sample.setMatrix(RMSE, MatrixFactory.linkToValue(error.getRMS()));
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
		Matrix input = sample.getMatrix(INPUT);
		Matrix weight = sample.getMatrix(WEIGHT);
		Matrix target = sample.getMatrix(TARGET);
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
			confusion = MatrixFactory.zeros(classCount, classCount);
		}

		for (Sample sample : dataSet.getSamples()) {

			predict(sample);

			if (evaluate) {
				double rmse = sample.getMatrix(RMSE).getEuklideanValue();
				error += Math.pow(rmse, 2.0);

				if ((sample instanceof ClassificationSample)) {

					int recognized = ((ClassificationSample) sample).getRecognizedClass();
					int targetClass = ((ClassificationSample) sample).getTargetClass();

					if (classCount == 1 || recognized == -1) {
						confusion.setAsDouble(confusion.getAsDouble(0, 0) + 1, 0, 0);
					} else {
						confusion.setAsDouble(confusion.getAsDouble(recognized, targetClass) + 1,
								recognized, targetClass);
					}

					if (((ClassificationSample) sample).isCorrect()) {
						correctCount++;
						// weight = weight * 0.99;
					} else {
						errorCount++;
						// weight = weight / 0.99;
					}

					// ((RegressionSample) sample).setWeight(weight);
				}

			}
		}

		Matrix outputError = MatrixFactory.linkToValue(Math.sqrt(error
				/ dataSet.getSamples().getSize()));
		outputError.setLabel("RMSE with " + getLabel());
		dataSet.appendRMSEMatrix(outputError);

		if ((dataSet instanceof ClassificationDataSet)) {
			confusion.setLabel("Confusion with " + getLabel());
			((ClassificationDataSet) dataSet).appendConfusionMatrix(confusion);

			Matrix accuracy = MatrixFactory.linkToValue((double) correctCount
					/ (double) dataSet.getSamples().getSize());
			accuracy.setLabel("Accuracy with " + getLabel());
			((ClassificationDataSet) dataSet).appendAccuracyMatrix(accuracy);

			Matrix errorMatrix = MatrixFactory.linkToValue(errorCount);
			errorMatrix.setLabel("Errors with " + getLabel());
			((ClassificationDataSet) dataSet).appendErrorCountMatrix(errorMatrix);
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
