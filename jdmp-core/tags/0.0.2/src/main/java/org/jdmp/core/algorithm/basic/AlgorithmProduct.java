package org.jdmp.core.algorithm.basic;

import java.util.ArrayList;
import java.util.List;

import org.jdmp.core.algorithm.AlgorithmTwoSources;
import org.ujmp.core.Matrix;

public class AlgorithmProduct extends AlgorithmTwoSources {
	private static final long serialVersionUID = 2620456326030867650L;

	public AlgorithmProduct() {
		super("Multiply");
		setDescription("target = source1 * source2");
	}

	public List<Matrix> calculate(List<Matrix> input) throws Exception {
		List<Matrix> result = new ArrayList<Matrix>();

		if (input == null || input.size() < 2) {
			return result;
		}

		Matrix source1 = input.get(SOURCE1);
		Matrix source2 = input.get(SOURCE2);

		result.add(source1.mtimes(source2));

		return result;

	}
}
