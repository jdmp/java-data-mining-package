package org.jdmp.matrix.calculation.entrywise.creators;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.DoubleCalculation;

public class Zeros extends DoubleCalculation {
	private static final long serialVersionUID = 6651797875513211513L;

	public Zeros(Matrix matrix) {
		super(matrix);
	}

	@Override
	public double getDouble(long... coordinates) {
		return 0.0;
	}

	public static Matrix calc(Matrix source) throws MatrixException {
		Matrix ret = Matrix.zeros(source.getSize());
		return ret;
	}

}
