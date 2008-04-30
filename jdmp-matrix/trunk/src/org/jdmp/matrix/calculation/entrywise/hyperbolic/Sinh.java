package org.jdmp.matrix.calculation.entrywise.hyperbolic;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.DoubleCalculation;

public class Sinh extends DoubleCalculation {
	private static final long serialVersionUID = 2083545526665186477L;

	public Sinh(Matrix matrix) {
		super(matrix);
	}

	@Override
	public double getDouble(long... coordinates) throws MatrixException {
		return Math.sinh(getSource().getDouble(coordinates));
	}

	public static Matrix calc(Matrix source) throws MatrixException {
		Matrix ret = Matrix.zeros(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			ret.setDouble(Math.sinh(source.getDouble(c)), c);
		}
		return ret;
	}

}
