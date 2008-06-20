package org.jdmp.core.matrix.wrappers;

import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.core.sample.Sample;
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
				return p.getMatrix(Sample.PREDICTED).getDouble(r, c);
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
				p.getMatrix(Sample.PREDICTED).setDouble(value, r, c);
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
