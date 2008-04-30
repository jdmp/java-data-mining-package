package org.jdmp.matrix.calculation.general.statistical;

import org.jdmp.matrix.Coordinates;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.DoubleCalculation;
import org.jdmp.matrix.calculation.general.missingvalues.CountMissing;
import org.jdmp.matrix.util.MathUtil;

public class Var extends DoubleCalculation {
	private static final long serialVersionUID = -6376910948253374396L;

	private Matrix mean = null;

	private Matrix missingCount = null;

	private boolean ignoreNaN = false;

	public Var(int dimension, boolean ignoreNaN, Matrix matrix) {
		super(dimension, matrix);
		this.ignoreNaN = ignoreNaN;
	}

	public double getDouble(long... coordinates) throws MatrixException {
		if (mean == null) {
			mean = getSource().calcNew(new Mean(getDimension(), ignoreNaN, getSource()));
		}
		if (missingCount == null) {
			missingCount = getSource().calcNew(new CountMissing(getDimension(), getSource()));
		}

		double sum = 0;

		if (ignoreNaN) {

			switch (getDimension()) {
			case ROW:
				for (long r = getSource().getSize()[ROW] - 1; r != -1; r--) {
					sum += Math.pow(MathUtil.ignoreNaN(getSource().getDouble(r, coordinates[COLUMN]))
							- mean.getDouble(0, coordinates[COLUMN]), 2.0);
				}
				return sum / (getSource().getRowCount() - missingCount.getDouble(0, coordinates[COLUMN]));
			case COLUMN:
				for (long c = getSource().getSize()[COLUMN] - 1; c != -1; c--) {
					sum += Math.pow(MathUtil.ignoreNaN(getSource().getDouble(coordinates[ROW], c))
							- mean.getDouble(coordinates[ROW], 0), 2.0);
				}
				return sum / (getSource().getColumnCount() - missingCount.getDouble(coordinates[ROW], 0));
			case ALL:
				for (long r = getSource().getSize()[ROW] - 1; r != -1; r--) {
					for (long c = getSource().getSize()[COLUMN] - 1; c != -1; c--) {
						sum += Math.pow(MathUtil.ignoreNaN(getSource().getDouble(r, c)) - mean.getDouble(0, 0), 2.0);
					}
				}
				return sum / (Coordinates.product(getSource().getSize()) - missingCount.getDouble(0, 0));
			default:
				return 0.0;
			}
		} else {
			switch (getDimension()) {
			case ROW:
				for (long r = getSource().getSize()[ROW] - 1; r != -1; r--) {
					sum += Math.pow((getSource().getDouble(r, coordinates[COLUMN]))
							- mean.getDouble(0, coordinates[COLUMN]), 2.0);
				}
				return sum / (getSource().getRowCount() - missingCount.getDouble(0, coordinates[COLUMN]));
			case COLUMN:
				for (long c = getSource().getSize()[COLUMN] - 1; c != -1; c--) {
					sum += Math.pow((getSource().getDouble(coordinates[ROW], c)) - mean.getDouble(coordinates[ROW], 0),
							2.0);
				}
				return sum / (getSource().getColumnCount() - missingCount.getDouble(coordinates[ROW], 0));
			case ALL:
				for (long r = getSource().getSize()[ROW] - 1; r != -1; r--) {
					for (long c = getSource().getSize()[COLUMN] - 1; c != -1; c--) {
						sum += Math.pow((getSource().getDouble(r, c)) - mean.getDouble(0, 0), 2.0);
					}
				}
				return sum / (Coordinates.product(getSource().getSize()) - missingCount.getDouble(0, 0));
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
