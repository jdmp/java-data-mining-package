package org.jdmp.matrix.calculation.general.missingvalues;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.DoubleCalculation;
import org.jdmp.matrix.calculation.general.statistical.Mean;
import org.jdmp.matrix.util.MathUtil;

public class ImputeMean extends DoubleCalculation {
	private static final long serialVersionUID = -3749987323095497386L;

	private Matrix mean = null;

	public ImputeMean(int dimension, Matrix matrix) {
		super(dimension, matrix);
	}

	public double getDouble(long... coordinates) throws MatrixException {
		if (mean == null) {
			mean = getSource().calcNew(new Mean(getDimension(), true, getSource()));
		}
		double v = getSource().getDouble(coordinates);
		if (MathUtil.isNaNOrInfinite(v)) {
			switch (getDimension()) {
			case ALL:
				return mean.getDouble(0, 0);
			case ROW:
				return mean.getDouble(0, coordinates[COLUMN]);
			case COLUMN:
				return mean.getDouble(coordinates[ROW], 0);
			}
		} else {
			return v;
		}
		return 0.0;
	}

}
