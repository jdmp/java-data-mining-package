package org.jdmp.core.algorithm.hashing;

import org.jdmp.core.dataset.ListDataSet;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;

public class HashRandomProjection extends AbstractHashing {
	private static final long serialVersionUID = 2795551342148850378L;

	private final int numberOfBits;
	Matrix randomVectors = null;

	public HashRandomProjection(int numberOfBits) {
		this.numberOfBits = numberOfBits;
	}

	public void reset() {
		randomVectors.clear();
	}

	public void train(ListDataSet dataSet) {
		final int featureCount = dataSet.getFeatureCount();
		randomVectors = Matrix.Factory.randn(featureCount, numberOfBits);
	}

	public Matrix hash(Matrix input) {
		Matrix result = input.toColumnVector(Ret.NEW).mtimes(randomVectors).ge(Ret.NEW, 0);
		return result;
	}

}
