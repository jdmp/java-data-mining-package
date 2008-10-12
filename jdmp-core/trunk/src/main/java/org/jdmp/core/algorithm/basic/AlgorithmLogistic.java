package org.jdmp.core.algorithm.basic;

import java.util.HashMap;
import java.util.Map;

import org.jdmp.core.algorithm.AlgorithmOneSource;
import org.ujmp.core.Matrix;
import org.ujmp.core.doublecalculation.entrywise.misc.LogisticFunction;
import org.ujmp.core.exceptions.MatrixException;

public class AlgorithmLogistic extends AlgorithmOneSource {
	private static final long serialVersionUID = -6185025728766094423L;

	public AlgorithmLogistic() {
		super("Logistic Function");
		setDescription("target = 1/(exp(-x)+1)");
	}

	public Map<Object, Matrix> calculate(Map<Object, Matrix> input) throws MatrixException {
		Map<Object, Matrix> result = new HashMap<Object, Matrix>();

		Matrix in = input.get(SOURCE);
		result.put(TARGET, in.calcNew(new LogisticFunction(in)));
		return result;
	}

}
