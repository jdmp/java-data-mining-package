package org.jdmp.core.algorithm.basic;

import java.util.ArrayList;
import java.util.List;

import org.jdmp.core.algorithm.AlgorithmOneSource;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.exceptions.MatrixException;

public class AlgorithmTranspose extends AlgorithmOneSource {
	private static final long serialVersionUID = 7132114769238742740L;

	public AlgorithmTranspose() {
		super("Transpose");
		setDescription("target = source^T");
	}

	public List<Matrix> calculate(List<Matrix> input) throws MatrixException {
		List<Matrix> result = new ArrayList<Matrix>();

		if (input == null || input.size() < 1) {
			return result;
		}

		Matrix in = input.get(SOURCE);
		result.add(in.transpose());
		return result;
	}

}
