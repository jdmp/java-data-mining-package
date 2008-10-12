package org.jdmp.core.algorithm.basic;

import java.util.HashMap;
import java.util.Map;

import org.jdmp.core.algorithm.AlgorithmOneSource;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.exceptions.MatrixException;

public class AlgorithmGauss extends AlgorithmOneSource {
	private static final long serialVersionUID = 3127916742763698423L;

	private double sigma = 1;

	private double mu = 0.0;

	public AlgorithmGauss() {
		super("Gaussian Distribution");
		setDescription("target = gauss(source)");
	}

	public Map<Object, Matrix> calculate(Map<Object, Matrix> input) throws MatrixException {
		Map<Object, Matrix> result = new HashMap<Object, Matrix>();

		Matrix in = MatrixFactory.copyFromMatrix(input.get(SOURCE));
		for (long[] c : in.allCoordinates()) {
			in.setAsDouble(getProbability(in.getAsDouble(c)), c);
		}

		result.put(TARGET, in);
		return result;
	}

	public double getProbability(double x) {
		return 1.0 / (sigma * Math.sqrt(2 * Math.PI))
				* Math.exp(-Math.pow(x - mu, 2) / (2 * sigma * sigma));
	}

}
