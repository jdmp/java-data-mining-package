package org.jdmp.matrix.calculation.entrywise.rounding;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.DoubleCalculation;

public class Ceil extends DoubleCalculation {
	private static final long serialVersionUID = 3050677685836703426L;

	public Ceil(Matrix matrix) {
		super(matrix);
	}

	@Override
	public double getDouble(long... coordinates) throws MatrixException {
		return Math.ceil(getSource().getDouble(coordinates));
	}

	public static Matrix calcNew(Matrix source) throws MatrixException {
		Matrix ret = Matrix.zeros(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			ret.setDouble(Math.ceil(source.getDouble(c)), c);
		}
		return ret;
	}

}
