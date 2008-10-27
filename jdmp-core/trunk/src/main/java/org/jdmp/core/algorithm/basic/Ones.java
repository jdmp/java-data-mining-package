package org.jdmp.core.algorithm.basic;

import java.util.HashMap;
import java.util.Map;

import org.jdmp.core.algorithm.AlgorithmTwoSources;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.exceptions.MatrixException;

public class Ones extends AlgorithmTwoSources {
	private static final long serialVersionUID = -7410191356948561970L;

	public Ones(Variable... variables) {
		super(variables);
		setDescription("FIlls a matrix with ones");
	}

	public Map<Object, Matrix> calculate(Map<Object, Matrix> input) throws MatrixException {
		Map<Object, Matrix> result = new HashMap<Object, Matrix>();

		Matrix source1 = input.get(SOURCE1);
		Matrix source2 = input.get(SOURCE2);

		result.put(TARGET, MatrixFactory.ones((long) source1.getDoubleValue(), (long) source2
				.getDoubleValue()));

		return result;

	}
}
