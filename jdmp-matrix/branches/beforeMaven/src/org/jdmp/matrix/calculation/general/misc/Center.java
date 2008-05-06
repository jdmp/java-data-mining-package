package org.jdmp.matrix.calculation.general.misc;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.DoubleCalculation;
import org.jdmp.matrix.calculation.general.statistical.Mean;

public class Center extends DoubleCalculation {
	private static final long serialVersionUID = -2400183861312141152L;

	private Matrix mean = null;

	private boolean ignoreNaN = false;

	public Center(boolean ignoreNaN, int dimension, Matrix matrix) {
		super(dimension, matrix);
		this.ignoreNaN = ignoreNaN;
	}

	public double getDouble(long... coordinates) throws MatrixException {
		if (mean == null) {
			mean = getSource().calcNew(new Mean(getDimension(), ignoreNaN, getSource()));
		}
		switch (getDimension()) {
		case ALL:
			return getSource().getDouble(coordinates) - mean.getDouble(0, 0);
		case ROW:
			return getSource().getDouble(coordinates) - mean.getDouble(0, coordinates[COLUMN]);
		case COLUMN:
			return getSource().getDouble(coordinates) - mean.getDouble(coordinates[ROW], 0);
		}
		return Double.NaN;
	}

}
