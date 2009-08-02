package org.jdmp.core.algorithm.regression;

import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.core.sample.Sample;
import org.ujmp.core.Matrix;

public interface Regressor {

	public static final Object INPUT = "Input";

	public static final Object WEIGHT = "Weight";

	public static final Object PREDICTED = "Predicted";

	public static final Object TARGET = "Target";

	public static final Object DIFFERENCE = "Difference";

	public static final Object RMSE = "RMSE";

	public void train(RegressionDataSet dataSet) throws Exception;

	public void reset() throws Exception;

	public void train(Matrix input, Matrix sampleWeight, Matrix target) throws Exception;

	public void predict(Sample sample) throws Exception;

	public Matrix predict(Matrix input) throws Exception;

	public void train(Matrix input, Matrix target) throws Exception;

	public Matrix predict(Matrix input, Matrix sampleWeight) throws Exception;

	public void train(Sample sample) throws Exception;

	public void predict(RegressionDataSet dataSet) throws Exception;

	public Regressor emptyCopy() throws Exception;

}
