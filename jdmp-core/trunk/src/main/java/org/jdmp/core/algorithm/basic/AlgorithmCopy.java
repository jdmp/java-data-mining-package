package org.jdmp.core.algorithm.basic;

import java.util.ArrayList;
import java.util.List;

import org.jdmp.core.algorithm.AlgorithmOneSource;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.MatrixFactory;

public class AlgorithmCopy extends AlgorithmOneSource {
	private static final long serialVersionUID = 4425454677556747249L;

	public AlgorithmCopy() {
		super("Copy");
		setDescription("target = source");
	}

	public List<Matrix> calculate(List<Matrix> input) throws MatrixException {
		List<Matrix> result = new ArrayList<Matrix>();

		if (input == null || input.size() < 1) {
			return result;
		}

		Matrix source = input.get(SOURCE);
		Matrix target = MatrixFactory.copyOf(source);

		result.add(target);

		return result;
	}
}
