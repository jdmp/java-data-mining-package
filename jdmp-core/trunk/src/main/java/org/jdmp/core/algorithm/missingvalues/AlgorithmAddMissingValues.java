package org.jdmp.core.algorithm.missingvalues;

import java.util.ArrayList;
import java.util.List;

import org.jdmp.core.algorithm.AlgorithmTwoSources;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.exceptions.MatrixException;

public class AlgorithmAddMissingValues extends AlgorithmTwoSources {
	private static final long serialVersionUID = 688352802013430077L;

	public static final int SOURCE = SOURCE1;

	public static final int PROBABILITY = SOURCE2;

	public AlgorithmAddMissingValues() {
		super("Add Missing Values");
		setDescription("x = NaN with probability p");
	}

	public static final void replace(Matrix source, double probability) throws MatrixException {
		for (long r = source.getRowCount() - 1; r != -1; r--) {
			for (long c = source.getColumnCount() - 1; c != -1; c--) {
				double rand = Math.random();
				if (rand < probability) {
					source.setAsDouble(Double.NaN, r, c);
				}
			}
		}
	}

	public List<Matrix> calculate(List<Matrix> matrices) throws MatrixException {
		List<Matrix> result = new ArrayList<Matrix>();

		Matrix source = matrices.get(SOURCE);
		Matrix probability = matrices.get(PROBABILITY);

		Matrix target = MatrixFactory.copyFromMatrix(source);
		AlgorithmAddMissingValues.replace(target, probability.getEuklideanValue());

		result.add(target);

		return result;
	}

}
