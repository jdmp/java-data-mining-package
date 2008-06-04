package org.jdmp.core.matrix.wrappers;

import org.jdmp.core.dataset.BasicDataSet;
import org.jdmp.core.sample.Sample;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.interfaces.Wrapper;
import org.jdmp.matrix.stubs.AbstractDenseDoubleMatrix2D;

public class DataSetInputMatrixWrapper extends AbstractDenseDoubleMatrix2D implements
		Wrapper<BasicDataSet> {
	private static final long serialVersionUID = -817570097594349208L;

	private BasicDataSet dataSet = null;

	public DataSetInputMatrixWrapper(BasicDataSet ds) {
		this.dataSet = ds;
	}

	public long[] getSize() {
		Sample p = dataSet.getSample(0);
		if (p == null) {
			return new long[] { 0, 0 };
		}
		Matrix input = p.getInputMatrix();
		if (input != null) {
			return new long[] { dataSet.getSampleCount(), input.getValueCount() };
		} else {
			return null;
		}

	}

	public double getDouble(long... coordinates) throws MatrixException {
		int row = (int) coordinates[ROW];
		int col = (int) coordinates[COLUMN];
		Sample p = dataSet.getSample(row);
		if (p != null) {
			if (p.getInputMatrix() != null) {
				long r = col / p.getInputMatrix().getColumnCount();
				long c = col % p.getInputMatrix().getColumnCount();
				return p.getInputMatrix().getDouble(r, c);
			}
		}
		return 0.0;
	}

	public void setDouble(double value, long... coordinates) throws MatrixException {
		int row = (int) coordinates[ROW];
		int col = (int) coordinates[COLUMN];
		Sample p = dataSet.getSample(row);
		if (p != null) {
			if (p.getInputMatrix() != null) {
				long r = col / p.getInputMatrix().getColumnCount();
				long c = col % p.getInputMatrix().getColumnCount();
				p.getInputMatrix().setDouble(value, r, c);
			}
		}
	}

	public BasicDataSet getWrappedObject() {
		return dataSet;
	}

	public void setWrappedObject(BasicDataSet object) {
		this.dataSet = object;
	}

}
