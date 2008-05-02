package org.jdmp.matrix.calculation.entrywise.basic;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.calculation.DoubleCalculation;

public class Power extends DoubleCalculation {
	private static final long serialVersionUID = -6766560469728046231L;

	public Power(Matrix matrix, Matrix power) {
		super(matrix, power);
	}

	public Power(Matrix m1, double v2) throws MatrixException {
		super(m1, MatrixFactory.fill(v2, m1.getSize()));
	}

	@Override
	public double getDouble(long... coordinates) throws MatrixException {
		return Math.pow(getSource().getDouble(coordinates), getSources()[1].getDouble(coordinates));
	}

	public static Matrix calc(Matrix source, Matrix power) throws MatrixException {
		Matrix ret = MatrixFactory.zeros(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			ret.setDouble(Math.pow(source.getDouble(c), power.getDouble(c)), c);
		}
		return ret;
	}

	public static Matrix calc(Matrix source, double power) throws MatrixException {
		Matrix ret = MatrixFactory.zeros(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			ret.setDouble(Math.pow(source.getDouble(c), power), c);
		}
		return ret;
	}

}
