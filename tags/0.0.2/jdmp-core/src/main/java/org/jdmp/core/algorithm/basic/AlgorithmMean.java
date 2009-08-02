package org.jdmp.core.algorithm.basic;

import java.util.ArrayList;
import java.util.List;

import org.jdmp.core.algorithm.AlgorithmOneSource;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.MathUtil;

public class AlgorithmMean extends AlgorithmOneSource {
	private static final long serialVersionUID = 5594989536534719762L;

	private int dimension = 0;

	public AlgorithmMean() {
		super("Mean");
		setDescription("target = mean(source)");
	}

	public AlgorithmMean(int dimension) {
		this();
		this.dimension = dimension;
	}

	public List<Matrix> calculate(List<Matrix> input) throws MatrixException {
		List<Matrix> result = new ArrayList<Matrix>();

		if (input == null || input.size() < 1) {
			return result;
		}

		Matrix source = input.get(SOURCE);
		Matrix meanMatrix = null;

		if (dimension == 0) {
			meanMatrix = MatrixFactory.zeros(1, source.getColumnCount());
			for (long c = source.getColumnCount() - 1; c != -1; c--) {
				double sum = 0;
				int count = 0;
				for (long r = source.getRowCount() - 1; r != -1; r--) {
					double v = source.getAsDouble(r, c);
					if (!MathUtil.isNaNOrInfinite(v)) {
						sum += v;
						count++;
					}
				}
				count = (count == 0) ? 1 : count;
				meanMatrix.setAsDouble(sum / count, 0, c);
			}
		} else {
			meanMatrix = MatrixFactory.zeros(source.getRowCount(), 1);
			double sum = 0.0, v = 0.0;
			int count = 0;
			long columns = source.getColumnCount();
			for (long c, r = source.getRowCount(); --r >= 0;) {
				sum = 0.0;
				count = 0;
				for (c = columns; --c >= 0;) {
					v = source.getAsDouble(r, c);
					if (!MathUtil.isNaNOrInfinite(v)) {
						sum += v;
						count++;
					}
				}
				count = (count == 0) ? 1 : count;
				meanMatrix.setAsDouble(sum / count, r, 0);
			}
		}

		result.add(meanMatrix);

		return result;
	}
}
