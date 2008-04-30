package org.jdmp.matrix.calculation.general.misc;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.DoubleCalculation;
import org.jdmp.matrix.calculation.general.statistical.Std;

public class Standardize extends DoubleCalculation {
	private static final long serialVersionUID = 6454174968175712888L;

	private Matrix center = null;

	private Matrix sigma = null;

	private boolean ignoreNaN = false;

	public Standardize(boolean ignoreNaN, int dimension, Matrix matrix) {
		super(dimension, matrix);
		this.ignoreNaN = ignoreNaN;
	}

	public double getDouble(long... coordinates) throws MatrixException {
		if (center == null) {
			center = getSource().calcNew(new Center(ignoreNaN, getDimension(), getSource()));
		}
		if (sigma == null) {
			sigma = center.calcNew(new Std(getDimension(), ignoreNaN, center));
		}
		switch (getDimension()) {
		case ALL:
			return center.getDouble(coordinates) / sigma.getDouble(0, 0);
		case ROW:
			return center.getDouble(coordinates) / sigma.getDouble(0, coordinates[COLUMN]);
		case COLUMN:
			return center.getDouble(coordinates) / sigma.getDouble(coordinates[ROW], 0);
		}
		return Double.NaN;
	}

}
