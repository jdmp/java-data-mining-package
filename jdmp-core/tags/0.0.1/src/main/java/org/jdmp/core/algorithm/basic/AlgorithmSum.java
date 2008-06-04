package org.jdmp.core.algorithm.basic;

import java.util.ArrayList;
import java.util.List;

import org.jdmp.core.algorithm.AlgorithmOneSource;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.calculation.Calculation.Ret;
import org.jdmp.matrix.exceptions.MatrixException;

public class AlgorithmSum extends AlgorithmOneSource {
	private static final long serialVersionUID = -6902623124007218820L;

	private int dimension = ROW;

	public AlgorithmSum() {
		super("Sum");
		setDescription("target = sum(source)");
	}

	public AlgorithmSum(int dimension) {
		this();
		this.dimension = dimension;
	}

	public List<Matrix> calculate(List<Matrix> input) throws MatrixException {
		List<Matrix> result = new ArrayList<Matrix>();

		if (input == null || input.size() < 1) {
			return result;
		}

		Matrix source = input.get(SOURCE);
		Matrix sumMatrix = source.sum(Ret.NEW, dimension, true);

		result.add(sumMatrix);

		return result;
	}

}
