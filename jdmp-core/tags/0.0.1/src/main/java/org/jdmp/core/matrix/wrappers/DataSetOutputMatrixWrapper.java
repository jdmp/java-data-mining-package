package org.jdmp.core.matrix.wrappers;

import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.core.sample.RegressionSample;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.interfaces.Wrapper;
import org.jdmp.matrix.stubs.AbstractDenseDoubleMatrix2D;

public class DataSetOutputMatrixWrapper extends AbstractDenseDoubleMatrix2D implements
		Wrapper<RegressionDataSet> {
	private static final long serialVersionUID = 5197402551973462998L;

	private RegressionDataSet dataSet = null;

	public DataSetOutputMatrixWrapper(RegressionDataSet ds) {
		this.dataSet = ds;
	}

	public long[] getSize() {
		RegressionSample p = dataSet.getSample(0);
		if (p == null) {
			return new long[] { 0, 0 };
		}
		Matrix output = p.getOutputMatrix();
		if (output != null) {
			return new long[] { dataSet.getSampleCount(), output.getValueCount() };
		} else {
			return new long[] { 0, 0 };
		}

	}

	public double getDouble(long... coordinates) throws MatrixException {
		int row = (int) coordinates[ROW];
		int col = (int) coordinates[COLUMN];
		RegressionSample p = dataSet.getSample(row);
		if (p != null) {
			if (p.getOutputMatrix() != null) {
				long r = col / p.getOutputMatrix().getColumnCount();
				long c = col % p.getOutputMatrix().getColumnCount();
				return p.getOutputMatrix().getDouble(r, c);
			}
		}
		return 0.0;
	}

	public void setDouble(double value, long... coordinates) throws MatrixException {
		int row = (int) coordinates[ROW];
		int col = (int) coordinates[COLUMN];
		RegressionSample p = dataSet.getSample(row);
		if (p != null) {
			if (p.getOutputErrorMatrix() != null) {
				long r = col / p.getOutputMatrix().getColumnCount();
				long c = col % p.getOutputMatrix().getColumnCount();
				p.getOutputMatrix().setDouble(value, r, c);
			}
		}
	}

	public RegressionDataSet getWrappedObject() {
		return dataSet;
	}

	public void setWrappedObject(RegressionDataSet object) {
		this.dataSet = object;
	}

}
