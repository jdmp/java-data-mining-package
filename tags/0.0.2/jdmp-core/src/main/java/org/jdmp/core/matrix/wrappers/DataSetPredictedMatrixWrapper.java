package org.jdmp.core.matrix.wrappers;

import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.core.sample.Sample;
import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;

public class DataSetPredictedMatrixWrapper extends AbstractDenseDoubleMatrix2D implements
		Wrapper<RegressionDataSet> {
	private static final long serialVersionUID = 5197402551973462998L;

	private RegressionDataSet dataSet = null;

	public DataSetPredictedMatrixWrapper(RegressionDataSet ds) {
		this.dataSet = ds;
	}

	public long[] getSize() {
		Sample p = dataSet.getSample(0);
		if (p == null) {
			return new long[] { 0, 0 };
		}
		Matrix output = p.getMatrix(Sample.PREDICTED);
		if (output != null) {
			return new long[] { dataSet.getSampleCount(), output.getValueCount() };
		} else {
			return new long[] { 0, 0 };
		}

	}

	public double getDouble(long row, long column) throws MatrixException {
		Sample p = dataSet.getSample((int) row);
		if (p != null) {
			if (p.getMatrix(Sample.PREDICTED) != null) {
				long r = column / p.getMatrix(Sample.PREDICTED).getColumnCount();
				long c = column % p.getMatrix(Sample.PREDICTED).getColumnCount();
				return p.getMatrix(Sample.PREDICTED).getAsDouble(r, c);
			}
		}
		return 0.0;
	}

	public void setDouble(double value, long row, long column) throws MatrixException {
		Sample p = dataSet.getSample((int) row);
		if (p != null) {
			if (p.getMatrix(Sample.PREDICTED) != null) {
				long r = column / p.getMatrix(Sample.PREDICTED).getColumnCount();
				long c = column % p.getMatrix(Sample.PREDICTED).getColumnCount();
				p.getMatrix(Sample.PREDICTED).setAsDouble(value, r, c);
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
