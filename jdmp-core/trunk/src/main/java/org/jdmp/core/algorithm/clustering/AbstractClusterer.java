package org.jdmp.core.algorithm.clustering;

import org.jdmp.core.algorithm.AbstractAlgorithm;
import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.core.sample.Sample;
import org.ujmp.core.Matrix;

public abstract class AbstractClusterer extends AbstractAlgorithm implements Clusterer {

	public AbstractClusterer(String label) {
		super(label);
	}

	public void predict(RegressionDataSet dataSet) throws Exception {
		for (Sample sample : dataSet.getSampleList()) {
			predict(sample);
		}
	}

	public final void predict(Sample sample) throws Exception {
		Matrix output = predict(sample.getMatrix(INPUT), sample.getMatrix(WEIGHT));
		sample.setMatrix(PREDICTED, output);
		// List<Matrix> error = getOutputErrorAlgorithm().calculate(output,
		// sample.getTargetMatrix());
		// sample.setOutputErrorMatrix(error.get(0));
		// sample.setRMSEMatrix(MatrixFactory.linkToValue(error.get(0).getRMS()));
	}

}
