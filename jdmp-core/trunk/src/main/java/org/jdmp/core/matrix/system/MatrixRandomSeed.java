package org.jdmp.core.matrix.system;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.stubs.AbstractDenseDoubleMatrix2D;
import org.jdmp.matrix.util.MathUtil;

public class MatrixRandomSeed extends AbstractDenseDoubleMatrix2D {
	private static final long serialVersionUID = 1950244958868637395L;

	private static Matrix matrix = null;

	private MatrixRandomSeed() {
	}

	public static Matrix getInstance() {
		if (matrix == null) {
			matrix = new MatrixRandomSeed();
		}
		return matrix;
	}

	public long[] getSize() {
		return new long[] { 1, 1 };
	}

	public double getDouble(long... coordinates) {
		return MathUtil.getSeed();
	}

	public void setDouble(double value, long... coordinates) {
		MathUtil.setSeed((long) value);
	}

}
