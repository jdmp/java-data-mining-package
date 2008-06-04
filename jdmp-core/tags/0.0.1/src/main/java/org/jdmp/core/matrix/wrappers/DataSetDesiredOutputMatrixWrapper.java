package org.jdmp.core.matrix.wrappers;

import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.core.sample.RegressionSample;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.interfaces.Wrapper;
import org.jdmp.matrix.stubs.AbstractDenseDoubleMatrix2D;

public class DataSetDesiredOutputMatrixWrapper extends AbstractDenseDoubleMatrix2D implements
		Wrapper<RegressionDataSet> {
	private static final long serialVersionUID = 5906451226992160036L;

	private RegressionDataSet dataSet = null;

	public DataSetDesiredOutputMatrixWrapper(RegressionDataSet ds) {
		this.dataSet = ds;
	}

	public long[] getSize() {
		RegressionSample p = dataSet.getSample(0);
		if (p == null) {
			return new long[] { 0, 0 };
		}
		return new long[] { dataSet.getSampleCount(), p.getDesiredOutputMatrix().getColumnCount() };
	}

	public double getDouble(long... coordinates) throws MatrixException {
		int row = (int) coordinates[ROW];
		int col = (int) coordinates[COLUMN];
		RegressionSample p = dataSet.getSample(row);
		if (p != null) {
			return p.getDesiredOutputMatrix().getDouble(0, col);
		} else {
			return 0.0;
		}
	}

	public void setDouble(double value, long... coordinates) throws MatrixException {
		int row = (int) coordinates[ROW];
		int col = (int) coordinates[COLUMN];
		RegressionSample p = dataSet.getSample(row);
		if (p != null) {
			p.getDesiredOutputMatrix().setDouble(value, 0, col);
		}
	}

	public RegressionDataSet getWrappedObject() {
		return dataSet;
	}

	public void setWrappedObject(RegressionDataSet object) {
		this.dataSet = object;
	}

}
