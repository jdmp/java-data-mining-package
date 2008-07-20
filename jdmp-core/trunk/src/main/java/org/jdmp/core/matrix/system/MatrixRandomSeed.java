package org.jdmp.core.matrix.system;

import org.ujmp.core.Matrix;
import org.ujmp.core.matrices.stubs.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.util.MathUtil;

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

	public double getDouble(long row, long column) {
		return MathUtil.getSeed();
	}

	public void setDouble(double value, long row, long column) {
		MathUtil.setSeed((long) value);
	}

}
