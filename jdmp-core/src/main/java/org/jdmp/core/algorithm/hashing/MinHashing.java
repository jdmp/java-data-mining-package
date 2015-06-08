package org.jdmp.core.algorithm.hashing;

import java.util.ArrayList;
import java.util.List;

import org.jdmp.core.dataset.ListDataSet;
import org.ujmp.core.Matrix;
import org.ujmp.core.util.MathUtil;

public class MinHashing extends AbstractHashing {
	private static final long serialVersionUID = -6746930977676653097L;

	private final int numberOfBits;
	private final int numberOfPermutations;

	private final HashRandomProjection hashRandomProjections;

	private final List<List<Integer>> permutations = new ArrayList<List<Integer>>();

	public MinHashing(int numberOfBits, int numberOfPermutations) {
		this.hashRandomProjections = new HashRandomProjection(numberOfBits);
		this.numberOfBits = numberOfBits;
		this.numberOfPermutations = numberOfPermutations;
	}

	public void reset() {
		hashRandomProjections.clear();
		permutations.clear();
	}

	public void train(ListDataSet dataSet) {
		hashRandomProjections.train(dataSet);
		for (int i = 0; i < numberOfPermutations; i++) {
			permutations.add(MathUtil.randPermInt(0, numberOfBits));
		}
	}

	public Matrix hash(Matrix input) {
		Matrix result = Matrix.Factory.zeros(1, numberOfPermutations);
		Matrix hash = hashRandomProjections.hash(input);
		for (int i = 0; i < numberOfPermutations; i++) {
			for (int j = 0; j < numberOfBits; j++) {
				int pos = permutations.get(i).get(j);
				if (hash.getAsBoolean(0, pos)) {
					result.setAsDouble(j, 0, i);
					break;
				}
			}
		}
		return result;
	}

}
