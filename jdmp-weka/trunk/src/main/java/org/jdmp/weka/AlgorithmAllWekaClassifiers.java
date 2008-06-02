package org.jdmp.weka;

import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.algorithm.classification.AlgorithmClassifier;
import org.jdmp.core.dataset.ClassificationDataSet;
import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.core.sample.ClassificationSample;
import org.jdmp.core.sample.Sample;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.weka.AlgorithmWekaClassifier.WekaClassifier;

public class AlgorithmAllWekaClassifiers extends AlgorithmClassifier {
	private static final long serialVersionUID = 7734147962054470988L;

	public AlgorithmAllWekaClassifiers() throws Exception {
		super("All Weka Classifiers");
		createAlgorithms();
	}

	private void createAlgorithms() throws Exception {
		getAlgorithmList().clear();
		for (WekaClassifier name : WekaClassifier.values()) {
			addAlgorithm(new AlgorithmWekaClassifier(name, false));
		}
	}

	public void predict(ClassificationDataSet dataSet) throws Exception {
		for (Algorithm a : getAlgorithmList()) {

			if (a instanceof AlgorithmClassifier) {

				int classCount = dataSet.getClassCount();
				Matrix confusion = MatrixFactory.zeros(classCount, classCount);
				double error = 0.0;
				int correctCount = 0;
				int errorCount = 0;
				for (Sample sample : dataSet.getSampleList()) {
					((AlgorithmClassifier) a).predict((ClassificationSample) sample);
					int desired = (int) ((ClassificationSample) sample).getDesiredOutputMatrix()
							.getCoordinatesOfMaximum()[COLUMN];
					int recognized = (int) ((ClassificationSample) sample).getOutputMatrix().getCoordinatesOfMaximum()[COLUMN];
					confusion.setDouble(confusion.getDouble(recognized, desired) + 1, recognized, desired);
					error += ((ClassificationSample) sample).getOutputErrorMatrix().getEuklideanValue();
					if (recognized == desired) {
						correctCount++;
					} else {
						errorCount++;
					}
				}
				Matrix outputError = MatrixFactory.linkToValue(error / dataSet.getSampleCount());
				outputError.setLabel("Output Error with " + a.getLabel());
				dataSet.appendRMSEMatrix(outputError);
				confusion.setLabel("Confusion with " + a.getLabel());
				dataSet.appendConfusionMatrix(confusion);
				Matrix accuracy = MatrixFactory.linkToValue((double) correctCount / (double) dataSet.getSampleCount());
				accuracy.setLabel("Accuracy with " + a.getLabel());
				dataSet.appendAccuracyMatrix(accuracy);
				Matrix errorMatrix = MatrixFactory.linkToValue((double) errorCount);
				errorMatrix.setLabel("Errors with " + a.getLabel());
				dataSet.appendErrorCountMatrix(errorMatrix);
			}
		}
	}

	public void train(RegressionDataSet dataSet) {
		for (Algorithm a : getAlgorithmList()) {
			if (a instanceof AlgorithmClassifier) {
				try {
					((AlgorithmClassifier) a).train(dataSet);
				} catch (Exception e) {
				}
			}
		}
	}

	@Override
	public Matrix predict(Matrix input, Matrix weight) {
		for (Algorithm a : getAlgorithmList()) {
			if (a instanceof AlgorithmClassifier) {
				try {
					((AlgorithmClassifier) a).predict(input, weight);
				} catch (Exception e) {
				}
			}
		}
		return null;
	}

	@Override
	public void train(Matrix input, Matrix sampleWeight, Matrix desiredOutput) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reset() throws Exception {
		createAlgorithms();
	}

}
