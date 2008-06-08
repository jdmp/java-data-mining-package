package org.jdmp.core.algorithm.clustering;

import java.util.List;

import org.jdmp.core.algorithm.AbstractAlgorithm;
import org.jdmp.matrix.Matrix;

public abstract class AbstractClusteringAlgorithm extends AbstractAlgorithm implements Clusterer {

	public AbstractClusteringAlgorithm(String label) {
		super(label);
	}

	@Override
	public List<Matrix> calculate(List<Matrix> matrices) throws Exception {
		return null;
	}

}
