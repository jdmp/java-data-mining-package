package org.jdmp.weka;

import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.core.sample.ClassificationSample;
import org.jdmp.core.sample.Sample;
import org.jdmp.matrix.MatrixException;

import weka.core.Instances;

public class DataSetToInstancesWrapper extends Instances {
	private static final long serialVersionUID = -7747912791607084086L;

	public DataSetToInstancesWrapper(RegressionDataSet dataSet, boolean discrete) throws MatrixException {
		super(dataSet.getLabel(), new DataSetToAttributeInfoWrapper(dataSet, discrete), dataSet
				.getSampleCount());
		setClassIndex(dataSet.getFeatureCount());

		for (Sample s : dataSet.getSampleList()) {
			add(new SampleToInstanceWrapper(((ClassificationSample) s).getInputMatrix(),
					((ClassificationSample) s).getWeightMatrix(), ((ClassificationSample) s)
							.getDesiredOutputMatrix(),discrete));
		}

	}

}
