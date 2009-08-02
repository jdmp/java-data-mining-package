package org.jdmp.core.algorithm.basic;

import java.util.ArrayList;
import java.util.List;

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

	public List<Matrix> calculate(List<Matrix> input) throws MatrixException {
		List<Matrix> result = new ArrayList<Matrix>();

		if (input == null || input.size() < 1) {
			return result;
		}

		Matrix in = input.get(SOURCE);
		result.add(in.calcNew(new LogisticFunction(in)));
		return result;
	}

}
