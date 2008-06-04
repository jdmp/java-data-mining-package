package org.jdmp.colt;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.interfaces.Wrapper;
import org.jdmp.matrix.stubs.AbstractDenseDoubleMatrix2D;

import cern.colt.matrix.impl.DenseDoubleMatrix2D;

public class ColtDenseDoubleMatrix2D extends AbstractDenseDoubleMatrix2D implements
		Wrapper<DenseDoubleMatrix2D> {
	private static final long serialVersionUID = -3223474248020842822L;

	private DenseDoubleMatrix2D matrix = null;

	public ColtDenseDoubleMatrix2D(long... size) {
		this.matrix = new DenseDoubleMatrix2D((int) size[ROW], (int) size[COLUMN]);
	}

	public ColtDenseDoubleMatrix2D(DenseDoubleMatrix2D m) {
		this.matrix = m;
	}

	public ColtDenseDoubleMatrix2D(Matrix source) throws MatrixException {
		this(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			setDouble(source.getDouble(c), c);
		}
	}

	public double getDouble(long... coordinates) {
		return matrix.getQuick((int) coordinates[ROW], (int) coordinates[COLUMN]);
	}

	public long[] getSize() {
		return new long[] { matrix.rows(), matrix.columns() };
	}

	public void setDouble(double value, long... coordinates) {
		matrix.setQuick((int) coordinates[ROW], (int) coordinates[COLUMN], value);
	}

	public DenseDoubleMatrix2D getWrappedObject() {
		return matrix;
	}

	public void setWrappedObject(DenseDoubleMatrix2D object) {
		this.matrix = object;
	}

}
