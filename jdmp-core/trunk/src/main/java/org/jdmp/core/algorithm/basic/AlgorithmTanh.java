package org.jdmp.core.algorithm.basic;

import java.util.ArrayList;
import java.util.List;

import org.jdmp.core.algorithm.AlgorithmOneSource;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.Calculation.Ret;

public class AlgorithmTanh extends AlgorithmOneSource {
	private static final long serialVersionUID = 1972442317406585099L;

	public AlgorithmTanh() {
		super("Tanh");
		setDescription("target = tanh(source)");
	}

	public List<Matrix> calculate(List<Matrix> input) throws MatrixException {
		List<Matrix> result = new ArrayList<Matrix>();

		if (input == null || input.size() < 1) {
			return result;
		}

		Matrix in = input.get(SOURCE);
		result.add(in.tanh(Ret.NEW));
		return result;
	}

}
