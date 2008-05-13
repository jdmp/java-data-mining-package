package org.jdmp.matrix.calculation.general.statistical;

import java.util.HashMap;
import java.util.Map;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.DoubleCalculation;
import org.jdmp.matrix.util.MathUtil;

public class MutualInformation extends DoubleCalculation {
	private static final long serialVersionUID = -4891250637894943873L;

	public MutualInformation(Matrix matrix) {
		super(matrix);
	}

	@Override
	public double getDouble(long... coordinates) throws MatrixException {
		return calculate(coordinates[ROW], coordinates[COLUMN], getSource());
	}

	@Override
	public long[] getSize() {
		return new long[] { getSource().getColumnCount(), getSource().getColumnCount() };
	}

	public static final double calculate(long var1, long var2, Matrix matrix) {
		double count = matrix.getRowCount();

		Map<Double, Double> count1 = new HashMap<Double, Double>();
		Map<Double, Double> count2 = new HashMap<Double, Double>();
		Map<String, Double> count12 = new HashMap<String, Double>();

		// count absolute frequency
		for (int r = 0; r < matrix.getRowCount(); r++) {
			double value1 = matrix.getDouble(r, var1);
			double value2 = matrix.getDouble(r, var2);

			Double c1 = count1.get(value1);
			c1 = (c1 == null) ? 0.0 : c1;
			count1.put(value1, c1 + 1.0);

			Double c2 = count2.get(value2);
			c2 = (c2 == null) ? 0.0 : c2;
			count2.put(value2, c2 + 1);

			Double c12 = count12.get(value1 + "," + value2);
			c12 = (c12 == null) ? 0.0 : c12;
			count12.put(value1 + "," + value2, c12 + 1);
		}

		// calculate relative frequency
		for (Double value1 : count1.keySet()) {
			Double c1 = count1.get(value1);
			count1.put(value1, c1 / count);
		}

		for (Double value2 : count2.keySet()) {
			Double c2 = count2.get(value2);
			count2.put(value2, c2 / count);
		}

		for (String value12 : count12.keySet()) {
			Double c12 = count12.get(value12);
			count12.put(value12, c12 / count);
		}

		// calculate mutual information
		double mutualInformation = 0.0;
		for (Double value1 : count1.keySet()) {
			double p1 = count1.get(value1);
			for (Double value2 : count2.keySet()) {
				double p2 = count2.get(value2);
				Double p12 = count12.get(value1 + "," + value2);
				if (p12 != null) {
					mutualInformation += p12 * MathUtil.log2(p12 / (p1 * p2));
				}
			}
		}

		// System.out.println(count1);
		// System.out.println(count2);
		// System.out.println(count12);
		// System.out.println(mutualInformation);

		return mutualInformation;
	}

}