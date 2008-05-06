package org.jdmp.matrix.calculation.general.missingvalues;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.DoubleCalculation;
import org.jdmp.matrix.util.MathUtil;

public class CountMissing extends DoubleCalculation {
	private static final long serialVersionUID = -8720603522373221865L;

	public CountMissing(int dimension, Matrix matrix) {
		super(dimension, matrix);
	}

	public double getDouble(long... coordinates) throws MatrixException {
		double sum = 0;
		switch (getDimension()) {
		case ROW:
			for (long r = getSource().getSize()[ROW] - 1; r != -1; r--) {
				sum += MathUtil.isNaNOrInfinite(getSource().getDouble(r, coordinates[COLUMN])) ? 1 : 0;
			}
			return sum;
		case COLUMN:
			for (long c = getSource().getSize()[COLUMN] - 1; c != -1; c--) {
				sum += MathUtil.isNaNOrInfinite(getSource().getDouble(coordinates[ROW], c)) ? 1 : 0;
			}
			return sum;
		case ALL:
			for (long r = getSource().getSize()[ROW] - 1; r != -1; r--) {
				for (long c = getSource().getSize()[COLUMN] - 1; c != -1; c--) {
					sum += MathUtil.isNaNOrInfinite(getSource().getDouble(r, c)) ? 1 : 0;
				}
			}
			return sum;
		}
		return 0.0;
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
