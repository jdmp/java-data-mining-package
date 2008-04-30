package org.jdmp.matrix.calculation.entrywise.trigonometric;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.DoubleCalculation;

public class Sin extends DoubleCalculation {
	private static final long serialVersionUID = -8127064720590287207L;

	public Sin(Matrix matrix) {
		super(matrix);
	}

	@Override
	public double getDouble(long... coordinates) throws MatrixException {
		return Math.sin(getSource().getDouble(coordinates));
	}

	public static Matrix calc(Matrix source) throws MatrixException {
		Matrix ret = Matrix.zeros(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			ret.setDouble(Math.sin(source.getDouble(c)), c);
		}
		return ret;
	}

}
