package org.jdmp.matrix.calculation.general.statistical;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.DoubleCalculation;

public class Std extends DoubleCalculation {
	private static final long serialVersionUID = 6318655294298955306L;

	private Matrix variance = null;

	private boolean ignoreNaN = false;

	public Std(int dimension, boolean ignoreNaN, Matrix matrix) {
		super(dimension, matrix);
		this.ignoreNaN = ignoreNaN;
	}

	public double getDouble(long... coordinates) throws MatrixException {
		if (variance == null) {
			variance = getSource().calcNew(new Var(getDimension(), ignoreNaN, getSource()));
		}
		return Math.sqrt(variance.getDouble(coordinates));
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
