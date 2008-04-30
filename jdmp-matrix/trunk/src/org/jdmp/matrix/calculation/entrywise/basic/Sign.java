package org.jdmp.matrix.calculation.entrywise.basic;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.DoubleCalculation;

public class Sign extends DoubleCalculation {
	private static final long serialVersionUID = 8479106978433813886L;

	public Sign(Matrix matrix) {
		super(matrix);
	}

	@Override
	public double getDouble(long... coordinates) throws MatrixException {
		return Math.signum(getSource().getDouble(coordinates));
	}

	public static Matrix calc(Matrix source) throws MatrixException {
		Matrix ret = Matrix.zeros(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			ret.setDouble(Math.signum(source.getDouble(c)), c);
		}
		return ret;
	}

}
