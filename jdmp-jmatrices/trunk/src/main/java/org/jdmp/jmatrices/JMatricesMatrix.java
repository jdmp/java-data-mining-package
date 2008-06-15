package org.jdmp.jmatrices;

import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.interfaces.Wrapper;
import org.jdmp.matrix.stubs.AbstractDenseDoubleMatrix2D;
import org.jmatrices.dbl.MatrixFactory;

public class JMatricesMatrix extends AbstractDenseDoubleMatrix2D implements
		Wrapper<org.jmatrices.dbl.Matrix> {
	private static final long serialVersionUID = 513251881654621L;

	private org.jmatrices.dbl.Matrix matrix = null;

	public JMatricesMatrix(org.jmatrices.dbl.Matrix matrix) {
		this.matrix = matrix;
	}

	public JMatricesMatrix(long... size) {
		this.matrix = MatrixFactory.getMatrix((int) size[ROW], (int) size[COLUMN], null);
	}

	public JMatricesMatrix(org.jdmp.matrix.Matrix source) throws MatrixException {
		this(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			setDouble(source.getDouble(c), c);
		}
	}

	public double getDouble(long... coordinates) {
		return matrix.get((int) coordinates[ROW], (int) coordinates[COLUMN]);
	}

	public long[] getSize() {
		return new long[] { matrix.rows(), matrix.cols() };
	}

	public void setDouble(double value, long... coordinates) {
		matrix.set((int) coordinates[ROW], (int) coordinates[COLUMN], value);
	}

	public org.jmatrices.dbl.Matrix getWrappedObject() {
		return matrix;
	}

	public void setWrappedObject(org.jmatrices.dbl.Matrix object) {
		this.matrix = object;
	}

}
