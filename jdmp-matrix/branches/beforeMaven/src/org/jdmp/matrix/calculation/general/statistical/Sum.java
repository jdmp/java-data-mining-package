package org.jdmp.matrix.calculation.general.statistical;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.DoubleCalculation;
import org.jdmp.matrix.util.MathUtil;

public class Sum extends DoubleCalculation {
	private static final long serialVersionUID = 1070433312716157660L;

	boolean ignoreNaN = false;

	public Sum(int dimension, boolean ignoreNaN, Matrix matrix) {
		super(dimension, matrix);
		this.ignoreNaN = ignoreNaN;
	}

	public double getDouble(long... coordinates) throws MatrixException {
		double sum = 0;

		if (ignoreNaN) {

			switch (getDimension()) {
			case ROW:
				for (long r = getSource().getSize()[ROW] - 1; r != -1; r--) {
					sum += MathUtil.ignoreNaN(getSource().getDouble(r, coordinates[COLUMN]));
				}
				return sum;
			case COLUMN:
				for (long c = getSource().getSize()[COLUMN] - 1; c != -1; c--) {
					sum += MathUtil.ignoreNaN(getSource().getDouble(coordinates[ROW], c));
				}
				return sum;
			case ALL:
				for (long r = getSource().getSize()[ROW] - 1; r != -1; r--) {
					for (long c = getSource().getSize()[COLUMN] - 1; c != -1; c--) {
						sum += MathUtil.ignoreNaN(getSource().getDouble(r, c));
					}
				}
				return sum;
			default:
				return 0.0;
			}
		} else {
			switch (getDimension()) {
			case ROW:
				for (long r = getSource().getSize()[ROW] - 1; r != -1; r--) {
					sum += getSource().getDouble(r, coordinates[COLUMN]);
				}
				return sum;
			case COLUMN:
				for (long c = getSource().getSize()[COLUMN] - 1; c != -1; c--) {
					sum += getSource().getDouble(coordinates[ROW], c);
				}
				return sum;
			case ALL:
				for (long r = getSource().getSize()[ROW] - 1; r != -1; r--) {
					for (long c = getSource().getSize()[COLUMN] - 1; c != -1; c--) {
						sum += getSource().getDouble(r, c);
					}
				}
				return sum;
			default:
				return 0.0;
			}
		}

	}

	public long[] getSize() {
		switch (getDimension()) {
		case ROW:
			return new long[] { 1, getSource().getSize()[COLUMN] };
		case COLUMN:
			return new long[] { getSource().getSize()[ROW], 1 };
		case ALL:
			return new long[] { 1, 1 };
		}
		return null;
	}

}
