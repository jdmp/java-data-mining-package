package org.jdmp.vecmath;

import javax.vecmath.GMatrix;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.interfaces.Wrapper;
import org.jdmp.matrix.stubs.AbstractDenseDoubleMatrix2D;

public class VecMathMatrix extends AbstractDenseDoubleMatrix2D implements Wrapper<GMatrix> {
	private static final long serialVersionUID = 3792684800581150214L;

	private GMatrix matrix = null;

	public VecMathMatrix(GMatrix m) {
		this.matrix = m;
	}

	public VecMathMatrix(long... size) {
		this.matrix = new GMatrix((int) size[ROW], (int) size[COLUMN]);
		// matrix is not empty by default!
		for (int r = 0; r < size[ROW]; r++) {
			for (int c = 0; c < size[COLUMN]; c++) {
				setDouble(0.0, r, c);
			}
		}
	}

	public VecMathMatrix(Matrix source) throws MatrixException {
		this(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			setDouble(source.getDouble(c), c);
		}
	}

	public double getDouble(long... coordinates) {
		return matrix.getElement((int) coordinates[ROW], (int) coordinates[COLUMN]);
	}

	public long[] getSize() {
		return new long[] { matrix.getNumRow(), matrix.getNumCol() };
	}

	public void setDouble(double value, long... coordinates) {
		matrix.setElement((int) coordinates[ROW], (int) coordinates[COLUMN], value);
	}

	public GMatrix getWrappedObject() {
		return matrix;
	}

	public void setWrappedObject(GMatrix object) {
		this.matrix = object;
	}

}
