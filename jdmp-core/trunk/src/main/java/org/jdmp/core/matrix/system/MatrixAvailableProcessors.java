package org.jdmp.core.matrix.system;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.stubs.AbstractDenseDoubleMatrix2D;

public class MatrixAvailableProcessors extends AbstractDenseDoubleMatrix2D {
	private static final long serialVersionUID = -3768846722489359117L;

	private static Matrix matrix = null;

	private MatrixAvailableProcessors() {
	}

	public static Matrix getInstance() {
		if (matrix == null) {
			matrix = new MatrixAvailableProcessors();
		}
		return matrix;
	}

	public long[] getSize() {
		return new long[] { 1, 1 };
	}

	public double getDouble(long row, long column) {
		return Runtime.getRuntime().availableProcessors();
	}

	public void setDouble(double value, long row, long column) {
	}

	public boolean isReadOnly() {
		return true;
	}

}
