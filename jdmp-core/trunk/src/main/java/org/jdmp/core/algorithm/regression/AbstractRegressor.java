package org.jdmp.core.algorithm.regression;

import java.util.List;

import org.jdmp.core.algorithm.AbstractAlgorithm;
import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.algorithm.basic.AlgorithmDifference;
import org.jdmp.core.dataset.ClassificationDataSet;
import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.core.sample.ClassificationSample;
import org.jdmp.core.sample.Sample;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixFactory;

public abstract class AbstractRegressor extends AbstractAlgorithm implements Regressor {

	public static final int OUTPUTERRORALGORITHM = 0;

	public static final int TRAIN = 0;

	public static final int PREDICT = 1;

	public int mode = TRAIN;

	public AbstractRegressor(String label) {
		super(label);
		setAlgorithm(OUTPUTERRORALGORITHM, new AlgorithmDifference());
	}

	public abstract void train(RegressionDataSet dataSet) throws Exception;

	public abstract void reset() throws Exception;

	public abstract void train(Matrix input, Matrix sampleWeight, Matrix desiredOutput)
			throws Exception;

	public final void predict(Sample sample) throws Exception {
		Matrix predicted = predict(sample.getMatrix(INPUT), sample.getMatrix(WEIGHT));
		sample.setMatrix(PREDICTED, predicted);
		List<Matrix> error = getOutputErrorAlgorithm().calculate(predicted,
				sample.getMatrix(TARGET));
		sample.setMatrix(DIFFERENCE, error.get(0));
		sample.setMatrix(RMSE, MatrixFactory.linkToValue(error.get(0).getRMS()));
	}

	public final Matrix predict(Matrix input) throws Exception {
		return predict(input.toRowVector(), null);
	}

	public final void train(Matrix input, Matrix desiredOutput) throws Exception {
		train(input, MatrixFactory.linkToValue(1.0), desiredOutput);
	}

	public abstract Matrix predict(Matrix input, Matrix sampleWeight) throws Exception;

	public final List<Matrix> calculate(List<Matrix> input) throws Exception {
		switch (getMode()) {
		case TRAIN:
			train(input.get(0), input.get(1), input.get(2));
			return null;
		case PREDICT:
			predict(input.get(0), input.get(1));
			return null;
		}
		return null;
	}

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

		for (Sample sample : dataSet.getSampleList()) {

			predict(sample);

			double rmse = sample.getMatrix(RMSE).getEuklideanValue();
			error += Math.pow(rmse, 2.0);

			if (sample instanceof ClassificationSample) {

				int recognized = ((ClassificationSample) sample).getRecognizedClass();
				int desired = ((ClassificationSample) sample).getDesiredClass();

				if (classCount == 1 || recognized == -1) {
					confusion.setDouble(confusion.getDouble(0, 0) + 1, 0, 0);
				} else {
					confusion.setDouble(confusion.getDouble(recognized, desired) + 1, recognized,
							desired);
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

		Matrix outputError = MatrixFactory.linkToValue(Math.sqrt(error / dataSet.getSampleCount()));
		outputError.setLabel("Output Error with " + getLabel());
		dataSet.appendRMSEMatrix(outputError);

		if (dataSet instanceof ClassificationDataSet) {
			confusion.setLabel("Confusion with " + getLabel());
			((ClassificationDataSet) dataSet).appendConfusionMatrix(confusion);

			Matrix accuracy = MatrixFactory.linkToValue((double) correctCount
					/ (double) dataSet.getSampleCount());
			accuracy.setLabel("Accuracy with " + getLabel());
			((ClassificationDataSet) dataSet).appendAccuracyMatrix(accuracy);

			Matrix errorMatrix = MatrixFactory.linkToValue((double) errorCount);
			errorMatrix.setLabel("Errors with " + getLabel());
			((ClassificationDataSet) dataSet).appendErrorCountMatrix(errorMatrix);
		}
	}

	public Algorithm getOutputErrorAlgorithm() {
		return getAlgorithm(OUTPUTERRORALGORITHM);
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
