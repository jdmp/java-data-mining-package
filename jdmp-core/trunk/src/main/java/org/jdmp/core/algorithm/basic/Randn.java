package org.jdmp.core.algorithm.basic;

import java.util.HashMap;
import java.util.Map;

import org.jdmp.core.algorithm.AlgorithmTwoSources;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.exceptions.MatrixException;

public class Randn extends AlgorithmTwoSources {
	private static final long serialVersionUID = -3218599515259241080L;

	public Randn(Variable... variables) {
		super(variables);
		setDescription("FIlls a matrix with gaussian values");
	}

	public Map<Object, Matrix> calculate(Map<Object, Matrix> input) throws MatrixException {
		Map<Object, Matrix> result = new HashMap<Object, Matrix>();

		Matrix source1 = input.get(SOURCE1);
		Matrix source2 = input.get(SOURCE2);

		result.put(TARGET, MatrixFactory.randn((long) source1.getDoubleValue(), (long) source2
				.getDoubleValue()));

		return result;

	}
}
