package org.jdmp.core.algorithm.basic;

import java.util.HashMap;
import java.util.Map;

import org.jdmp.core.algorithm.AlgorithmTwoSources;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;

public class Minus extends AlgorithmTwoSources {
	private static final long serialVersionUID = 2476001103906791183L;

	public Minus(Variable... variables) {
		super(variables);
		setDescription("target = source1 - source2");
	}

	public Map<Object, Matrix> calculate(Map<Object, Matrix> input) throws MatrixException {
		Map<Object, Matrix> result = new HashMap<Object, Matrix>();
		Matrix source1 = input.get(SOURCE1);
		Matrix source2 = input.get(SOURCE2);
		Matrix target = new org.ujmp.core.doublecalculation.basic.Minus(source1, source2).calcNew();
		result.put(TARGET, target);
		return result;
	}

}
