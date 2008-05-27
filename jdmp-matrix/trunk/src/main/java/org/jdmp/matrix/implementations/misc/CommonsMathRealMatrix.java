package org.jdmp.matrix.implementations.misc;

import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.linear.RealMatrixImpl;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.interfaces.Wrapper;
import org.jdmp.matrix.stubs.AbstractDenseDoubleMatrix2D;

public class CommonsMathRealMatrix extends AbstractDenseDoubleMatrix2D implements Wrapper<RealMatrixImpl> {
	private static final long serialVersionUID = -1161807620507675926L;

	private RealMatrixImpl matrix = null;

	public CommonsMathRealMatrix(long... size) {
		matrix = new RealMatrixImpl((int) size[ROW], (int) size[COLUMN]);
	}

	public CommonsMathRealMatrix(org.jdmp.matrix.Matrix source) throws MatrixException {
		this(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			setDouble(source.getDouble(c), c);
		}
	}

	public CommonsMathRealMatrix(RealMatrix matrix) {
		if (matrix instanceof RealMatrixImpl) {
			this.matrix = (RealMatrixImpl) matrix;
		} else {
			throw new MatrixException("Can oly use RealMatrixImpl");
		}
	}

	public RealMatrixImpl getWrappedObject() {
		return matrix;
	}

	public void setWrappedObject(RealMatrixImpl object) {
		this.matrix = object;
	}

	public double getDouble(long... coordinates) throws MatrixException {
		return matrix.getEntry((int) coordinates[ROW], (int) coordinates[COLUMN]);
	}

	public void setDouble(double value, long... coordinates) throws MatrixException {
		matrix.getDataRef()[(int) coordinates[ROW]][(int) coordinates[COLUMN]] = value;
	}

	public long[] getSize() {
		return new long[] { matrix.getRowDimension(), matrix.getColumnDimension() };
	}

	public Matrix inv() {
		return new CommonsMathRealMatrix(matrix.inverse());
	}

}
