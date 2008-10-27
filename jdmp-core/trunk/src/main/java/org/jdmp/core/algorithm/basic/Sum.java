package org.jdmp.core.algorithm.basic;

import java.util.HashMap;
import java.util.Map;

import org.jdmp.core.algorithm.AlgorithmOneSource;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.exceptions.MatrixException;

public class Sum extends AlgorithmOneSource {
	private static final long serialVersionUID = -6902623124007218820L;

	public Sum(Variable... variables) {
		super(variables);
		setDescription("target = sum(source)");
	}

	public Map<Object, Matrix> calculate(Map<Object, Matrix> input) throws MatrixException {
		Map<Object, Matrix> result = new HashMap<Object, Matrix>();
		Matrix source = input.get(SOURCE);
		Matrix target = source.sum(Ret.NEW, Matrix.ROW, true);
		result.put(TARGET, target);
		return result;
	}

}
