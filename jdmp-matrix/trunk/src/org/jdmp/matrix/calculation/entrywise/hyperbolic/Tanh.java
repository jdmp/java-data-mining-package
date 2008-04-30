package org.jdmp.matrix.calculation.entrywise.hyperbolic;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.DoubleCalculation;

public class Tanh extends DoubleCalculation {
	private static final long serialVersionUID = -3681404429953396643L;

	public Tanh(Matrix matrix) {
		super(matrix);
	}

	@Override
	public double getDouble(long... coordinates) throws MatrixException {
		return Math.tanh(getSource().getDouble(coordinates));
	}

	public static Matrix calc(Matrix source) throws MatrixException {
		Matrix ret = Matrix.zeros(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			ret.setDouble(Math.tanh(source.getDouble(c)), c);
		}
		return ret;
	}
}
