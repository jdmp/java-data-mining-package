package org.jdmp.jama;

import org.jdmp.matrix.DefaultAnnotation;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.interfaces.Wrapper;
import org.jdmp.matrix.stubs.AbstractDenseDoubleMatrix2D;

import Jama.Matrix;

public class JamaMatrix extends AbstractDenseDoubleMatrix2D implements Wrapper<Jama.Matrix> {
	private static final long serialVersionUID = -6065454603299978242L;

	private Jama.Matrix matrix = null;

	private DefaultAnnotation annotation = null;

	public JamaMatrix(long... size) {
		matrix = new Jama.Matrix((int) size[ROW], (int) size[COLUMN]);
	}

	public JamaMatrix(org.jdmp.matrix.Matrix source) throws MatrixException {
		this(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			setDouble(source.getDouble(c), c);
		}
	}

	public double getDouble(long... coordinates) {
		return matrix.get((int) coordinates[ROW], (int) coordinates[COLUMN]);
	}

	public long[] getSize() {
		return new long[] { matrix.getRowDimension(), matrix.getColumnDimension() };
	}

	public void setDouble(double value, long... coordinates) {
		matrix.set((int) coordinates[ROW], (int) coordinates[COLUMN], value);
	}

	public Matrix getWrappedObject() {
		return matrix;
	}

	public void setWrappedObject(Matrix object) {
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
