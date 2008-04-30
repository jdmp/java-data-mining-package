package org.jdmp.matrix.calculation.entrywise.basic;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.DoubleCalculation;

public class Sqrt extends DoubleCalculation {
	private static final long serialVersionUID = -8139556053923421840L;

	public Sqrt(Matrix matrix) {
		super(matrix);
	}

	@Override
	public double getDouble(long... coordinates) throws MatrixException {
		return Math.sqrt(getSource().getDouble(coordinates));
	}

	public static Matrix calc(Matrix source) throws MatrixException {
		Matrix ret = Matrix.zeros(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			ret.setDouble(Math.sqrt(source.getDouble(c)), c);
		}
		return ret;
	}

}
