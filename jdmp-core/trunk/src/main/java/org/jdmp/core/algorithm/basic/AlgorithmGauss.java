package org.jdmp.core.algorithm.basic;

import java.util.ArrayList;
import java.util.List;

import org.jdmp.core.algorithm.AlgorithmOneSource;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.exceptions.MatrixException;

public class AlgorithmGauss extends AlgorithmOneSource {
	private static final long serialVersionUID = 3127916742763698423L;

	private double sigma = 1;

	private double mu = 0.0;

	public AlgorithmGauss() {
		super("Gaussian Distribution");
		setDescription("target = gauss(source)");
	}

	public List<Matrix> calculate(List<Matrix> input) throws MatrixException {
		List<Matrix> result = new ArrayList<Matrix>();

		if (input == null || input.size() < 1) {
			return result;
		}

		Matrix in = MatrixFactory.copyOf(input.get(SOURCE));
		for (long[] c : in.allCoordinates()) {
			in.setDouble(getProbability(in.getDouble(c)), c);
		}

		result.add(in);
		return result;
	}

	public double getProbability(double x) {
		return 1.0 / (sigma * Math.sqrt(2 * Math.PI)) * Math.exp(-Math.pow(x - mu, 2) / (2 * sigma * sigma));
	}

}
