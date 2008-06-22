package org.jdmp.core.matrix.wrappers;

import org.jdmp.core.sample.Sample;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.interfaces.Wrapper;
import org.jdmp.matrix.stubs.AbstractDenseDoubleMatrix2D;

public class SampleInputOutputMatrix extends AbstractDenseDoubleMatrix2D implements Wrapper<Sample> {
	private static final long serialVersionUID = -8005076916609420357L;

	private Sample sample = null;

	public SampleInputOutputMatrix(Sample sample) {
		this.sample = sample;
	}

	public Matrix getInputMatrix() {
		Matrix m = sample.getMatrix(Sample.INPUT);
		return (m == null) ? MatrixFactory.linkToValue(0) : m;
	}

	public Matrix getTargetMatrix() {
		Matrix m = sample.getMatrix(Sample.TARGET);
		return (m == null) ? MatrixFactory.linkToValue(0) : m;
	}

	public long[] getSize() {
		return new long[] {
				Math.max(getInputMatrix().getRowCount(), getTargetMatrix().getRowCount()),
				getInputMatrix().getColumnCount() + getTargetMatrix().getColumnCount() };
	}

	public double getDouble(long row, long column) throws MatrixException {
		if (column < getInputMatrix().getColumnCount()) {
			return getInputMatrix().getDouble(row, column);
		} else {
			column -= getInputMatrix().getColumnCount();
			return getTargetMatrix().getDouble(row, column);
		}
	}

	public void setDouble(double value, long row, long column) throws MatrixException {
		if (column < getInputMatrix().getColumnCount()) {
			getInputMatrix().setDouble(value, row, column);
		} else {
			column -= getInputMatrix().getColumnCount();
			getTargetMatrix().setDouble(value, row, column);
		}
	}

	public Sample getWrappedObject() {
		return sample;
	}

	public void setWrappedObject(Sample object) {
		this.sample = object;
	}

}
