package org.jdmp.core.matrix.wrappers;

import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.core.sample.Sample;
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
		Sample p = dataSet.getSample(0);
		if (p == null) {
			return new long[] { 0, 0 };
		}
		return new long[] { dataSet.getSampleCount(), p.getMatrix(Sample.TARGET).getColumnCount() };
	}

	public double getDouble(long row, long column) throws MatrixException {
		Sample p = dataSet.getSample((int) row);
		if (p != null) {
			return p.getMatrix(Sample.TARGET).getDouble(0, column);
		} else {
			return 0.0;
		}
	}

	public void setDouble(double value, long row, long column) throws MatrixException {
		Sample p = dataSet.getSample((int) row);
		if (p != null) {
			p.getMatrix(Sample.TARGET).setDouble(value, 0, column);
		}
	}

	public RegressionDataSet getWrappedObject() {
		return dataSet;
	}

	public void setWrappedObject(RegressionDataSet object) {
		this.dataSet = object;
	}

}
