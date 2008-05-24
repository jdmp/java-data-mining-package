package org.jdmp.vecmath;

import javax.vecmath.GMatrix;

import org.jdmp.matrix.DefaultAnnotation;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.interfaces.Wrapper;
import org.jdmp.matrix.stubs.AbstractDenseDoubleMatrix2D;

public class VecMathMatrix extends AbstractDenseDoubleMatrix2D implements Wrapper<GMatrix> {
	private static final long serialVersionUID = 3792684800581150214L;

	private DefaultAnnotation annotation = null;

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

	public Object getMatrixAnnotation() {
		return annotation == null ? null : annotation.getMatrixAnnotation();
	}

	public void setMatrixAnnotation(Object value) {
		if (annotation == null) {
			annotation = new DefaultAnnotation();
		}
		annotation.setMatrixAnnotation(value);
	}

	public Object getAxisAnnotation(int axis, int positionOnAxis) {
		return annotation == null ? null : annotation.getAxisAnnotation(axis, positionOnAxis);
	}

	public Object getAxisAnnotation(int axis) {
		return annotation == null ? null : annotation.getAxisAnnotation(axis);
	}

	public void setAxisAnnotation(int axis, int positionOnAxis, Object value) {
		if (annotation == null) {
			annotation = new DefaultAnnotation();
		}
		annotation.setAxisAnnotation(axis, positionOnAxis, value);
	}

	public void setAxisAnnotation(int axis, Object value) {
		if (annotation == null) {
			annotation = new DefaultAnnotation();
		}
		annotation.setAxisAnnotation(axis, value);
	}

}
