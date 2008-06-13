package org.jdmp.weka;

import org.jdmp.core.algorithm.classification.Classifier;
import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.core.sample.ClassificationSample;
import org.jdmp.core.sample.Sample;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.exceptions.MatrixException;

import weka.core.Instances;

public class DataSetToInstancesWrapper extends Instances {
	private static final long serialVersionUID = -7747912791607084086L;

	public static final Object INPUT = Classifier.INPUT;
	public static final Object WEIGHT = Classifier.WEIGHT;
	public static final Object TARGET = Classifier.TARGET;

	public DataSetToInstancesWrapper(RegressionDataSet dataSet, boolean discrete)
			throws MatrixException {
		super(dataSet.getLabel(), new DataSetToAttributeInfoWrapper(dataSet, discrete), dataSet
				.getSampleCount());
		setClassIndex(dataSet.getFeatureCount());

		for (Sample s : dataSet.getSampleList()) {
			Matrix input = ((ClassificationSample) s).getMatrix(INPUT);
			Matrix weight = ((ClassificationSample) s).getMatrix(WEIGHT);
			Matrix target = ((ClassificationSample) s).getMatrix(TARGET);
			add(new SampleToInstanceWrapper(input, weight, target, discrete));
		}

	}

}
