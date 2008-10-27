package org.jdmp.core.algorithm.basic;

import java.util.HashMap;
import java.util.Map;

import org.jdmp.core.algorithm.AlgorithmTwoSources;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.exceptions.MatrixException;

public class Zeros extends AlgorithmTwoSources {
	private static final long serialVersionUID = -6698288195664853309L;

	public Zeros(Variable... variables) {
		super(variables);
		setDescription("FIlls a matrix with zeros");
	}

	public Map<Object, Matrix> calculate(Map<Object, Matrix> input) throws MatrixException {
		Map<Object, Matrix> result = new HashMap<Object, Matrix>();

		Matrix source1 = input.get(SOURCE1);
		Matrix source2 = input.get(SOURCE2);

		result.put(TARGET, MatrixFactory.zeros((long) source1.getDoubleValue(), (long) source2
				.getDoubleValue()));

		return result;

	}
}
