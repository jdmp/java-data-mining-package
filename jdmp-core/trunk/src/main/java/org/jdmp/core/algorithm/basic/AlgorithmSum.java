package org.jdmp.core.algorithm.basic;

import java.util.HashMap;
import java.util.Map;

import org.jdmp.core.algorithm.AlgorithmOneSource;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.exceptions.MatrixException;

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

	public Map<Object, Matrix> calculate(Map<Object, Matrix> input) throws MatrixException {
		Map<Object, Matrix> result = new HashMap<Object, Matrix>();

		Matrix source = input.get(SOURCE);
		Matrix sumMatrix = source.sum(Ret.NEW, dimension, true);

		result.put(TARGET, sumMatrix);

		return result;
	}

}
