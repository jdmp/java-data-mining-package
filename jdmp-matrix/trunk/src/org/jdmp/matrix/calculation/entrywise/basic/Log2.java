package org.jdmp.matrix.calculation.entrywise.basic;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.calculation.DoubleCalculation;

public class Log2 extends DoubleCalculation {
	private static final long serialVersionUID = 1858516096849343584L;

	public Log2(Matrix matrix) {
		super(matrix);
	}

	@Override
	public double getDouble(long... coordinates) throws MatrixException {
		double v = getSource().getDouble(coordinates);
		return Math.log(v) / Math.log(2.0);
	}

	public static Matrix calc(Matrix source) throws MatrixException {
		Matrix ret = MatrixFactory.zeros(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			double v = source.getDouble(c);
			ret.setDouble(Math.log(v) / Math.log(2.0), c);
		}
		return ret;
	}

}
