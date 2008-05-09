package org.jdmp.matrix.implementations.misc;

import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.linear.RealMatrixImpl;
import org.jdmp.matrix.DefaultAnnotation;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.interfaces.Annotation;
import org.jdmp.matrix.interfaces.Wrapper;
import org.jdmp.matrix.stubs.AbstractDenseDoubleMatrix2D;

public class CommonsMathRealMatrix extends AbstractDenseDoubleMatrix2D implements Wrapper<RealMatrix> {
	private static final long serialVersionUID = -1161807620507675926L;

	private RealMatrix matrix = null;

	private Annotation annotation = null;

	public CommonsMathRealMatrix(long... size) {
		matrix = new RealMatrixImpl((int) size[ROW], (int) size[COLUMN]);
	}

	public CommonsMathRealMatrix(org.jdmp.matrix.Matrix source) throws MatrixException {
		this(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			setDouble(source.getDouble(c), c);
		}
	}

	public RealMatrix getWrappedObject() {
		return matrix;
	}

	public void setWrappedObject(RealMatrix object) {
		this.matrix = object;
	}

	public double getDouble(long... coordinates) throws MatrixException {
		return matrix.getData()[(int) coordinates[ROW]][(int) coordinates[COLUMN]];
	}

	public void setDouble(double value, long... coordinates) throws MatrixException {
		matrix.getData()[(int) coordinates[ROW]][(int) coordinates[COLUMN]] = value;
	}

	public long[] getSize() {
		return new long[] { matrix.getRowDimension(), matrix.getColumnDimension() };
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
