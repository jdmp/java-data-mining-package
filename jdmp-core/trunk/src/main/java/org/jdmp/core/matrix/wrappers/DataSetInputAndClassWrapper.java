package org.jdmp.core.matrix.wrappers;

import org.jdmp.core.algorithm.regression.Regressor;
import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.core.sample.Sample;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.interfaces.Wrapper;
import org.jdmp.matrix.stubs.AbstractDenseDoubleMatrix2D;

public class DataSetInputAndClassWrapper extends AbstractDenseDoubleMatrix2D implements
		Wrapper<RegressionDataSet> {
	private static final long serialVersionUID = 7464288604917584889L;

	public static final Object INPUT = Regressor.INPUT;

	private RegressionDataSet dataSet = null;

	public DataSetInputAndClassWrapper(RegressionDataSet ds) {
		this.dataSet = ds;
	}

	public double getDouble(long row, long column) throws MatrixException {
		Sample p = dataSet.getSample((int) row);
		if (p != null) {
			if (column < getSize()[COLUMN] - 1) {
				return p.getMatrix(INPUT).getDouble(0, column);
			}
		}
		return 0.0;
	}

	public long[] getSize() {
		Sample p = dataSet.getSample(0);
		if (p == null) {
			return new long[] { 0, 0 };
		} else {
			return new long[] { dataSet.getSampleCount(), p.getMatrix(INPUT).getColumnCount() + 1 };
		}
	}

	public void setDouble(double value, long row, long column) {
	}

	public RegressionDataSet getWrappedObject() {
		return dataSet;
	}

	public void setWrappedObject(RegressionDataSet object) {
		this.dataSet = object;
	}

}
