package org.jdmp.core.algorithm.regression;

import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.core.sample.RegressionSample;
import org.jdmp.matrix.Matrix;

public interface Regressor {

	public static final String INPUT = "Input";

	public static final String WEIGHT = "Weight";

	public static final String PREDICTED = "Predicted";

	public static final String TARGET = "Target";

	public static final String DIFFERENCE = "Difference";

	public static final String RMSE = "RMSE";

	public void train(RegressionDataSet dataSet) throws Exception;

	public void reset() throws Exception;

	public void train(Matrix input, Matrix sampleWeight, Matrix target) throws Exception;

	public void predict(RegressionSample sample) throws Exception;

	public Matrix predict(Matrix input) throws Exception;

	public void train(Matrix input, Matrix target) throws Exception;

	public Matrix predict(Matrix input, Matrix sampleWeight) throws Exception;

	public void train(RegressionSample sample) throws Exception;

	public void predict(RegressionDataSet dataSet) throws Exception;

}
