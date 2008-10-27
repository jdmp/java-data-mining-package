package org.jdmp.core.algorithm.basic;

import java.util.HashMap;
import java.util.Map;

import org.jdmp.core.algorithm.AlgorithmOneSource;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.exceptions.MatrixException;

public class Mean extends AlgorithmOneSource {
	private static final long serialVersionUID = 5594989536534719762L;

	public Mean(Variable... variables) {
		super(variables);
		setDescription("target = mean(source)");
	}

	public Map<Object, Matrix> calculate(Map<Object, Matrix> input) throws MatrixException {
		Map<Object, Matrix> result = new HashMap<Object, Matrix>();
		Matrix source = input.get(SOURCE);
		Matrix target = source.mean(Ret.NEW, Matrix.ROW, true);
		result.put(TARGET, target);
		return result;
	}
}
