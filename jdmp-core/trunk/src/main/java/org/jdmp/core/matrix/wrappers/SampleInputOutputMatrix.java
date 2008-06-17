package org.jdmp.core.matrix.wrappers;

import org.jdmp.core.sample.RegressionSample;
import org.jdmp.core.sample.Sample;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.interfaces.Wrapper;
import org.jdmp.matrix.stubs.AbstractDenseDoubleMatrix2D;

public class SampleInputOutputMatrix extends AbstractDenseDoubleMatrix2D implements
		Wrapper<RegressionSample> {
	private static final long serialVersionUID = -8005076916609420357L;

	private RegressionSample sample = null;

	public SampleInputOutputMatrix(RegressionSample sample) {
		this.sample = sample;
	}

	public Matrix getInputMatrix() {
		Matrix m = sample.getMatrix(Sample.INPUT);
		return (m == null) ? MatrixFactory.linkToValue(0) : m;
	}

	public Matrix getDesiredOutputMatrix() {
		Matrix m = sample.getMatrix(Sample.TARGET);
		return (m == null) ? MatrixFactory.linkToValue(0) : m;
	}

	public long[] getSize() {
		return new long[] {
				Math.max(getInputMatrix().getRowCount(), getDesiredOutputMatrix().getRowCount()),
				getInputMatrix().getColumnCount() + getDesiredOutputMatrix().getColumnCount() };
	}

	public double getDouble(long... coordinates) throws MatrixException {
		if (coordinates[COLUMN] < getInputMatrix().getColumnCount()) {
			return getInputMatrix().getDouble(coordinates);
		} else {
			coordinates[COLUMN] -= getInputMatrix().getColumnCount();
			return getDesiredOutputMatrix().getDouble(coordinates);
		}
	}

	public void setDouble(double value, long... coordinates) throws MatrixException {
		if (coordinates[COLUMN] < getInputMatrix().getColumnCount()) {
			getInputMatrix().setDouble(value, coordinates);
		} else {
			coordinates[COLUMN] -= getInputMatrix().getColumnCount();
			getDesiredOutputMatrix().setDouble(value, coordinates);
		}
	}

	public RegressionSample getWrappedObject() {
		return sample;
	}

	public void setWrappedObject(RegressionSample object) {
		this.sample = object;
	}

}
