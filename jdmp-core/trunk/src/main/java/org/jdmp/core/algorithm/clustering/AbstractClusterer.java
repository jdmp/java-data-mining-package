package org.jdmp.core.algorithm.clustering;

import java.util.List;

import org.jdmp.core.algorithm.AbstractAlgorithm;
import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.core.sample.RegressionSample;
import org.jdmp.core.sample.Sample;
import org.jdmp.matrix.Matrix;

public abstract class AbstractClusterer extends AbstractAlgorithm implements Clusterer {

	public AbstractClusterer(String label) {
		super(label);
	}

	@Override
	public List<Matrix> calculate(List<Matrix> matrices) throws Exception {
		return null;
	}

	public void predict(RegressionDataSet dataSet) throws Exception {
		for (Sample sample : dataSet.getSampleList()) {
			predict((RegressionSample) sample);
		}
	}

	public final void predict(RegressionSample sample) throws Exception {
		Matrix output = predict(sample.getMatrix(INPUT), sample.getMatrix(WEIGHT));
		sample.setMatrix(PREDICTED, output);
		// List<Matrix> error = getOutputErrorAlgorithm().calculate(output,
		// sample.getDesiredOutputMatrix());
		// sample.setOutputErrorMatrix(error.get(0));
		// sample.setRMSEMatrix(MatrixFactory.linkToValue(error.get(0).getRMS()));
	}

}
