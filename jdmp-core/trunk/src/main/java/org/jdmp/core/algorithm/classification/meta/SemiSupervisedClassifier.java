package org.jdmp.core.algorithm.classification.meta;

import org.jdmp.core.algorithm.classification.Classifier;
import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.matrix.Matrix;

public interface SemiSupervisedClassifier {

	public Matrix predict(Matrix input, Matrix sampleWeight) throws Exception;

	public void reset() throws Exception;

	public void train(RegressionDataSet labeledData, RegressionDataSet unlabeledData)
			throws Exception;

	public void predict(RegressionDataSet dataSet) throws Exception;

	public Classifier emptyCopy() throws Exception;
}
