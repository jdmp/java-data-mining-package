package org.jdmp.core.matrix.wrappers;

import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.core.sample.Sample;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.interfaces.Wrapper;
import org.jdmp.matrix.stubs.AbstractDenseDoubleMatrix2D;

public class DataSetInputOutputMatrixWrapper extends AbstractDenseDoubleMatrix2D implements Wrapper<RegressionDataSet> {
	private static final long serialVersionUID = 677010635477854113L;

	private RegressionDataSet dataSet = null;

	public DataSetInputOutputMatrixWrapper(RegressionDataSet ds) {
		this.dataSet = ds;
	}

	public long[] getSize() {
		Sample p = dataSet.getSample(0);
		if (p == null) {
			return new long[] { 0, 0 };
		} else {
			return new long[] { dataSet.getSampleCount(), p.getMatrix().getColumnCount() };
		}
	}

	public double getDouble(long... coordinates) throws MatrixException {
		int row = (int) coordinates[ROW];
		int col = (int) coordinates[COLUMN];
		Sample p = dataSet.getSample(row);
		if (p != null) {
			return p.getMatrix().getDouble(0, col);
		} else {
			return 0.0;
		}
	}

	public void setDouble(double value, long... coordinates) throws MatrixException {
		int row = (int) coordinates[ROW];
		int col = (int) coordinates[COLUMN];
		Sample p = dataSet.getSample(row);
		if (p != null) {
			p.getMatrix().setDouble(value, 0, col);
		}
	}

	public Object getMatrixAnnotation() {
		return "Input and DesiredOutput";
	}

	public RegressionDataSet getWrappedObject() {
		return dataSet;
	}

	public void setWrappedObject(RegressionDataSet object) {
		this.dataSet = object;
	}

}
