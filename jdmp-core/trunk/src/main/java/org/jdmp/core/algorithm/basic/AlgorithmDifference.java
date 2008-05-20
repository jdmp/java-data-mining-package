package org.jdmp.core.algorithm.basic;

import java.util.ArrayList;
import java.util.List;

import org.jdmp.core.algorithm.AlgorithmTwoSources;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;

public class AlgorithmDifference extends AlgorithmTwoSources {
	private static final long serialVersionUID = 2476001103906791183L;

	public AlgorithmDifference() {
		super("Difference");
		setDescription("target = source1 - source2");
	}

	public List<Matrix> calculate(List<Matrix> input) throws MatrixException {
		List<Matrix> result = new ArrayList<Matrix>();

		if (input == null || input.size() < 2) {
			return result;
		}

		Matrix source1 = input.get(SOURCE1);
		Matrix source2 = input.get(SOURCE2);

		result.add(source1.minus(source2));

		return result;
	}

}
