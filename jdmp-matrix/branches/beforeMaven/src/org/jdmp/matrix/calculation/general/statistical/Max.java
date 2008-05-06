package org.jdmp.matrix.calculation.general.statistical;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.DoubleCalculation;

public class Max extends DoubleCalculation {
	private static final long serialVersionUID = -132801357103800951L;

	public Max(int dimension, Matrix matrix) {
		super(dimension, matrix);
	}

	public double getDouble(long... coordinates) throws MatrixException {
		double max = -Double.MAX_VALUE;
		switch (getDimension()) {
		case ROW:
			for (long r = getSource().getSize()[ROW] - 1; r != -1; r--) {
				double v = getSource().getDouble(r, coordinates[COLUMN]);
				if (v > max) {
					max = v;
				}
			}
			return max;
		case COLUMN:
			for (long c = getSource().getSize()[COLUMN] - 1; c != -1; c--) {
				double v = getSource().getDouble(coordinates[ROW], c);
				if (v > max) {
					max = v;
				}
			}
			return max;
		case ALL:
			for (long r = getSource().getSize()[ROW] - 1; r != -1; r--) {
				for (long c = getSource().getSize()[COLUMN] - 1; c != -1; c--) {
					double v = getSource().getDouble(r, c);
					if (v > max) {
						max = v;
					}
				}
			}
			return max;
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
		double max = -Double.MAX_VALUE;
		double v = 0.0;
		for (long[] c : m.availableCoordinates()) {
			max = (v = m.getDouble(c)) > max ? v : max;
		}
		return max;
	}
}
