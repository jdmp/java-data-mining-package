package org.jdmp.matrix.calculation.general.statistical;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.DoubleCalculation;

public class Min extends DoubleCalculation {
	private static final long serialVersionUID = 2209647170194250477L;

	public Min(int dimension, Matrix matrix) {
		super(dimension, matrix);
	}

	public double getDouble(long... coordinates) throws MatrixException {
		double min = Double.MAX_VALUE;
		switch (getDimension()) {
		case ROW:
			for (long r = getSource().getSize()[ROW] - 1; r != -1; r--) {
				double v = getSource().getDouble(r, coordinates[COLUMN]);
				if (v < min) {
					min = v;
				}
			}
			return min;
		case COLUMN:
			for (long c = getSource().getSize()[COLUMN] - 1; c != -1; c--) {
				double v = getSource().getDouble(coordinates[ROW], c);
				if (v < min) {
					min = v;
				}
			}
			return min;
		case ALL:
			for (long r = getSource().getSize()[ROW] - 1; r != -1; r--) {
				for (long c = getSource().getSize()[COLUMN] - 1; c != -1; c--) {
					double v = getSource().getDouble(r, c);
					if (v < min) {
						min = v;
					}
				}
			}
			return min;
		}
		return 0.0;
	}

	public long[] getSize() {
		switch (getDimension()) {
		case ROW:
			return new long[] { 1, getSource().getSize()[COLUMN] };
		case COLUMN:
			return new long[] { getSource().getSize()[ROW], 1 };
		}
		return null;
	}

	public static double calc(Matrix m) throws MatrixException {
		double min = Double.MAX_VALUE;
		double v = 0.0;
		for (long[] c : m.availableCoordinates()) {
			min = (v = m.getDouble(c)) < min ? v : min;
		}
		return min;
	}

}
