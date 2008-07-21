package org.jdmp.jama;

import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;
import org.ujmp.core.matrices.stubs.AbstractDenseDoubleMatrix2D;

import Jama.Matrix;

public class JamaMatrix extends AbstractDenseDoubleMatrix2D implements Wrapper<Jama.Matrix> {
	private static final long serialVersionUID = -6065454603299978242L;

	private Jama.Matrix matrix = null;

	public JamaMatrix(long... size) {
		matrix = new Jama.Matrix((int) size[ROW], (int) size[COLUMN]);
	}

	public JamaMatrix(org.ujmp.core.Matrix source) throws MatrixException {
		this(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			setAsDouble(source.getAsDouble(c), c);
		}
	}

	public double getDouble(long row, long column) {
		return matrix.get((int) row, (int) column);
	}

	public long[] getSize() {
		return new long[] { matrix.getRowDimension(), matrix.getColumnDimension() };
	}

	public void setDouble(double value, long row, long column) {
		matrix.set((int) row, (int) column, value);
	}

	public Matrix getWrappedObject() {
		return matrix;
	}

	public void setWrappedObject(Matrix object) {
		this.matrix = object;
	}

}
