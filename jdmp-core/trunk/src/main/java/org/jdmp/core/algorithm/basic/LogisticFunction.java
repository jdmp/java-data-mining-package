package org.jdmp.core.algorithm.basic;

import java.util.HashMap;
import java.util.Map;

import org.jdmp.core.algorithm.AlgorithmOneSource;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;

public class LogisticFunction extends AlgorithmOneSource {
	private static final long serialVersionUID = -6185025728766094423L;

	public LogisticFunction(Variable... variables) {
		super(variables);
		setDescription("target = 1/(exp(-x)+1)");
	}

	public Map<Object, Matrix> calculate(Map<Object, Matrix> input) throws MatrixException {
		Map<Object, Matrix> result = new HashMap<Object, Matrix>();

		Matrix in = input.get(SOURCE);
		result.put(TARGET, new org.ujmp.core.doublecalculation.entrywise.misc.LogisticFunction(in)
				.calcNew());
		return result;
	}

}
