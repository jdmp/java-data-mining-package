package org.jdmp.core.algorithm.regression;

import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.core.sample.RegressionSample;
import org.jdmp.matrix.Matrix;

public interface Regressor {

	public void train(RegressionDataSet dataSet) throws Exception;

	public void reset() throws Exception;

	public void train(Matrix input, Matrix sampleWeight, Matrix desiredOutput) throws Exception;

	public void predict(RegressionSample sample) throws Exception;

	public Matrix predict(Matrix input) throws Exception;

	public void train(Matrix input, Matrix desiredOutput) throws Exception;

	public Matrix predict(Matrix input, Matrix sampleWeight) throws Exception;

	public void train(RegressionSample sample) throws Exception;

	public void predict(RegressionDataSet dataSet) throws Exception;

}
