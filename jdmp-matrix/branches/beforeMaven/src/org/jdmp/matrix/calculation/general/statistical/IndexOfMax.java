package org.jdmp.matrix.calculation.general.statistical;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.DoubleCalculation;

public class IndexOfMax extends DoubleCalculation {
	private static final long serialVersionUID = 2656643557116576004L;

	public IndexOfMax(int dimension, Matrix matrix) {
		super(dimension, matrix);
	}

	public double getDouble(long... coordinates) throws MatrixException {
		double max = -Double.MAX_VALUE;
		long index = -1;
		switch (getDimension()) {
		case ROW:
			for (long r = getSource().getSize()[ROW] - 1; r != -1; r--) {
				double v = getSource().getDouble(r, coordinates[COLUMN]);
				if (v > max) {
					max = v;
					index = r;
				}
			}
			return index;
		case COLUMN:
			for (long c = getSource().getSize()[COLUMN] - 1; c != -1; c--) {
				double v = getSource().getDouble(coordinates[ROW], c);
				if (v > max) {
					max = v;
					index = c;
				}
			}
			return index;
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

}
