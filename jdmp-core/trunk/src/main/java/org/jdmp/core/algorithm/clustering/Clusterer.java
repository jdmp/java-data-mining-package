package org.jdmp.core.algorithm.clustering;

import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.core.sample.RegressionSample;
import org.jdmp.core.sample.Sample;
import org.jdmp.matrix.Matrix;

public interface Clusterer {

	public static final Object INPUT = Sample.INPUT;

	public static final Object WEIGHT = Sample.WEIGHT;

	public static final Object PREDICTED = Sample.PREDICTED;

	public void reset() throws Exception;

	public void train(RegressionDataSet dataSet) throws Exception;

	public void predict(RegressionSample sample) throws Exception;

	public void predict(RegressionDataSet dataSet) throws Exception;

	public Matrix predict(Matrix input, Matrix sampleWeight) throws Exception;

	public void setNumberOfClusters(int numberOfClusters) throws Exception;

}
