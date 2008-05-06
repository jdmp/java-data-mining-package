package org.jdmp.matrix.calculation.entrywise.trigonometric;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.calculation.DoubleCalculation;

public class Cos extends DoubleCalculation {
	private static final long serialVersionUID = 5733248311765384359L;

	public Cos(Matrix matrix) {
		super(matrix);
	}

	@Override
	public double getDouble(long... coordinates) throws MatrixException {
		return Math.cos(getSource().getDouble(coordinates));
	}

	public static Matrix calc(Matrix source) throws MatrixException {
		Matrix ret = MatrixFactory.zeros(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			ret.setDouble(Math.cos(source.getDouble(c)), c);
		}
		return ret;
	}

}
