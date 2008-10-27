package org.jdmp.core.algorithm.basic;

import java.util.HashMap;
import java.util.Map;

import org.jdmp.core.algorithm.AlgorithmOneSource;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.exceptions.MatrixException;

public class Abs extends AlgorithmOneSource {
	private static final long serialVersionUID = 8963883977304138269L;

	public Abs(Variable... variables) {
		super(variables);
		setDescription("target = abs(source)");
	}

	public Map<Object, Matrix> calculate(Map<Object, Matrix> input) throws MatrixException {
		Map<Object, Matrix> result = new HashMap<Object, Matrix>();

		Matrix source = input.get(SOURCE);

		result.put(TARGET, source.abs(Ret.NEW));

		return result;

	}
}
