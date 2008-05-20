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

	public double getDouble(long... coordinates) {
		return Runtime.getRuntime().availableProcessors();
	}

	public void setDouble(double value, long... coordinates) {
	}

	public boolean isReadOnly() {
		return true;
	}

	public Object getMatrixAnnotation() {
		return "Available Processors";
	}

}
