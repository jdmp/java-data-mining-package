package org.jdmp.core.algorithm.clustering;

import java.util.List;

import org.jdmp.core.algorithm.AbstractAlgorithm;
import org.jdmp.matrix.Matrix;

public abstract class AbstractClusterer extends AbstractAlgorithm implements Clusterer {

	public AbstractClusterer(String label) {
		super(label);
	}

	@Override
	public List<Matrix> calculate(List<Matrix> matrices) throws Exception {
		return null;
	}

}
