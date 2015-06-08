package org.jdmp.core.algorithm.hashing;

import java.util.ArrayList;
import java.util.List;

import org.jdmp.core.dataset.ListDataSet;
import org.ujmp.core.Matrix;
import org.ujmp.core.util.MathUtil;

public class LocalitySensitiveHashing extends AbstractHashing {
	private static final long serialVersionUID = 4449292116298947107L;

	private final int numberOfAndCombinations;
	private final int numberOfOrCombinations;
	private final int numberOfBits;

	private final HashRandomProjection hashRandomProjections;

	private final List<List<Integer>> permutations = new ArrayList<List<Integer>>();

	public LocalitySensitiveHashing() {
		this(4, 5, 20);
	}

	public LocalitySensitiveHashing(int numberOfBits) {
		this(numberOfBits, 5, 20);
	}

	public LocalitySensitiveHashing(int numberOfBits, int numberOfAndCombinations,
			int numberOfOrCombinations) {
		this.hashRandomProjections = new HashRandomProjection(numberOfAndCombinations
				* numberOfOrCombinations);
		this.numberOfAndCombinations = numberOfAndCombinations;
		this.numberOfOrCombinations = numberOfOrCombinations;
		this.numberOfBits = numberOfBits;
	}

	public void reset() {
		hashRandomProjections.clear();
	}

	public void train(ListDataSet dataSet) {
		hashRandomProjections.train(dataSet);
		for (int i = 0; i < numberOfBits; i++) {
			permutations.add(MathUtil.randPermInt(0, numberOfOrCombinations
					* numberOfAndCombinations));
		}

	}

	public Matrix hash(Matrix input) {
		Matrix result = Matrix.Factory.zeros(1, numberOfBits);
		Matrix hash = hashRandomProjections.hash(input);
		for (int b = 0; b < numberOfBits; b++) {
			int pos = 0;
			boolean or = false;
			for (int i = 0; i < numberOfOrCombinations; i++) {
				pos++;
				boolean and = true;
				for (int j = 1; j < numberOfAndCombinations; j++) {
					if (hash.getAsBoolean(0, permutations.get(b).get(pos - 1)) != hash
							.getAsBoolean(0, permutations.get(b).get(pos))) {
						and = false;
					}
					pos++;
				}
				or = or | and;
			}
			result.setAsBoolean(or, 0, b);
		}
		return result;
	}

}
