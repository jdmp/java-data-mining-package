package org.jdmp.core.algorithm.basic;

import java.util.HashMap;
import java.util.Map;

import org.jdmp.core.algorithm.AlgorithmOneSource;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.exceptions.MatrixException;

public class AlgorithmCopy extends AlgorithmOneSource {
	private static final long serialVersionUID = 4425454677556747249L;

	public AlgorithmCopy() {
		super("Copy");
		setDescription("target = source");
	}

	public Map<Object, Matrix> calculate(Map<Object, Matrix> input) throws MatrixException {
		Map<Object, Matrix> result = new HashMap<Object, Matrix>();

		Matrix source = input.get(SOURCE);
		Matrix target = MatrixFactory.copyFromMatrix(source);
		result.put(TARGET, target);

		return result;
	}
}
