package org.jdmp.core.algorithm.basic;

import java.util.HashMap;
import java.util.Map;

import org.jdmp.core.algorithm.AlgorithmTwoSources;
import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;

public class Plus extends AlgorithmTwoSources {
	private static final long serialVersionUID = -5630277400123182400L;

	public Plus() {
		super("Plus");
		setDescription("target = source1 + source2");
	}

	public Map<Object, Matrix> calculate(Map<Object, Matrix> input) throws MatrixException {
		Map<Object, Matrix> result = new HashMap<Object, Matrix>();

		Matrix source1 = input.get(SOURCE1);
		Matrix source2 = input.get(SOURCE2);
		result.put(TARGET, source1.plus(source2));

		return result;
	}

}
