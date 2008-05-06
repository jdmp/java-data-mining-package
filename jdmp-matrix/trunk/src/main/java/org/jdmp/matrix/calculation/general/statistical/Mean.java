package org.jdmp.matrix.calculation.general.statistical;

import java.util.logging.Level;

import org.jdmp.matrix.Coordinates;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.DoubleCalculation;
import org.jdmp.matrix.calculation.general.missingvalues.CountMissing;

public class Mean extends DoubleCalculation {
	private static final long serialVersionUID = 4116408128004680574L;

	private Matrix sum = null;

	private Matrix missingCount = null;

	private boolean ignoreNaN = false;

	public Mean(int dimension, boolean ignoreNaN, Matrix matrix) {
		super(dimension, matrix);
		this.ignoreNaN = ignoreNaN;
	}

	public double getDouble(long... coordinates) throws MatrixException {
		if (sum == null) {
			try {
				sum = getSource().calcNew(new Sum(getDimension(), ignoreNaN, getSource()));
			} catch (MatrixException e) {
				logger.log(Level.WARNING, "could not calculate Matrix", e);
			}
		}
		if (ignoreNaN && missingCount == null) {
			try {
				missingCount = getSource().calcNew(new CountMissing(getDimension(), getSource()));
			} catch (MatrixException e) {
				logger.log(Level.WARNING, "could not calculate Matrix", e);
			}
		}

		if (ignoreNaN) {
			switch (getDimension()) {
			case ALL:
				return sum.getDouble(0, 0)
						/ (Coordinates.product(getSource().getSize()) - missingCount.getDouble(0, 0));
			case ROW:
				return sum.getDouble(0, coordinates[COLUMN])
						/ (getSource().getRowCount() - missingCount.getDouble(0, coordinates[COLUMN]));
			case COLUMN:
				return sum.getDouble(coordinates[ROW], 0)
						/ (getSource().getColumnCount() - missingCount.getDouble(coordinates[ROW], 0));
			default:
				return Double.NaN;
			}

		} else {

			switch (getDimension()) {
			case ALL:
				return sum.getDouble(0, 0) / (Coordinates.product(getSource().getSize()));
			case ROW:
				return sum.getDouble(0, coordinates[COLUMN]) / (getSource().getRowCount());
			case COLUMN:
				return sum.getDouble(coordinates[ROW], 0) / (getSource().getColumnCount());
			default:
				return Double.NaN;
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
	
	
	public static double calc(Matrix m) throws MatrixException {
        double sum = 0.0;
        for (long[] c : m.availableCoordinates()) {
            sum+= m.getDouble(c);
        }
        return sum/m.getValueCount();
    }

}
