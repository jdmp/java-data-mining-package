package org.jdmp.matrix.calculation.entrywise.basic;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.calculation.DoubleCalculation;

public class Abs extends DoubleCalculation {
	private static final long serialVersionUID = 6393277198816850597L;

	public Abs(Matrix matrix) {
		super(matrix);
	}

	@Override
	public double getDouble(long... coordinates) throws MatrixException {
		return Math.abs(getSource().getDouble(coordinates));
	}

	public static Matrix calc(Matrix source) throws MatrixException {
		Matrix ret = MatrixFactory.zeros(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			ret.setDouble(Math.abs(source.getDouble(c)), c);
		}
		return ret;
	}

}
