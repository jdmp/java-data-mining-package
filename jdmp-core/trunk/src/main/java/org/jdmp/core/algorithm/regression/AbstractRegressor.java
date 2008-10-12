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

public abstract class AbstractRegressor extends AbstractAlgorithm implements Regressor {

	public static final int OUTPUTERRORALGORITHM = 0;

	public static final int TRAIN = 0;

	public static final int PREDICT = 1;

	public int mode = TRAIN;

	private boolean evaluate = true;

	public AbstractRegressor(String label) {
		super(label);
		setAlgorithm(OUTPUTERRORALGORITHM, new Minus());
	}

	public AbstractRegressor(String label, boolean evaluate) {
		this(label);
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
			Matrix error = getOutputErrorAlgorithm().calculate(predicted, sample.getMatrix(TARGET))
					.values().iterator().next();
			sample.setMatrix(DIFFERENCE, error);
			sample.setMatrix(RMSE, MatrixFactory.linkToValue(error.getRMS()));
		}
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

		for (Sample sample : dataSet.getSampleList()) {

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
				/ dataSet.getSampleList().getSize()));
		outputError.setLabel("Output Error with " + getLabel());
		dataSet.appendRMSEMatrix(outputError);

		if ((dataSet instanceof ClassificationDataSet)) {
			confusion.setLabel("Confusion with " + getLabel());
			((ClassificationDataSet) dataSet).appendConfusionMatrix(confusion);

			Matrix accuracy = MatrixFactory.linkToValue((double) correctCount
					/ (double) dataSet.getSampleList().getSize());
			accuracy.setLabel("Accuracy with " + getLabel());
			((ClassificationDataSet) dataSet).appendAccuracyMatrix(accuracy);

			Matrix errorMatrix = MatrixFactory.linkToValue(errorCount);
			errorMatrix.setLabel("Errors with " + getLabel());
			((ClassificationDataSet) dataSet).appendErrorCountMatrix(errorMatrix);
		}

	}

	public Algorithm getOutputErrorAlgorithm() {
		return getAlgorithmList().get(OUTPUTERRORALGORITHM);
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
