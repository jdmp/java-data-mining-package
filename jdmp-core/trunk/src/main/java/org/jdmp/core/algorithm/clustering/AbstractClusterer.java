package org.jdmp.core.algorithm.clustering;

import java.util.List;

import org.jdmp.core.algorithm.AbstractAlgorithm;
import org.jdmp.core.dataset.ClassificationDataSet;
import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.core.sample.ClassificationSample;
import org.jdmp.core.sample.RegressionSample;
import org.jdmp.core.sample.Sample;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixFactory;

public abstract class AbstractClusterer extends AbstractAlgorithm implements Clusterer {

	public AbstractClusterer(String label) {
		super(label);
	}

	@Override
	public List<Matrix> calculate(List<Matrix> matrices) throws Exception {
		return null;
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

			predict((RegressionSample) sample);

			double rmse = ((RegressionSample) sample).getRMSEVariable().getEuklideanValue();
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

	public final void predict(RegressionSample sample) throws Exception {
		Matrix output = predict(sample.getMatrix(INPUT), sample.getMatrix(WEIGHT));
		sample.setOutputMatrix(output);
		// List<Matrix> error = getOutputErrorAlgorithm().calculate(output,
		// sample.getDesiredOutputMatrix());
		// sample.setOutputErrorMatrix(error.get(0));
		// sample.setRMSEMatrix(MatrixFactory.linkToValue(error.get(0).getRMS()));
	}

}
