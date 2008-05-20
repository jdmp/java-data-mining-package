package org.jdmp.core.matrix.wrappers;

import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.core.sample.RegressionSample;
import org.jdmp.core.sample.Sample;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.interfaces.Wrapper;
import org.jdmp.matrix.stubs.AbstractDenseDoubleMatrix2D;

public class DataSetInputAndClassWrapper extends AbstractDenseDoubleMatrix2D implements Wrapper<RegressionDataSet> {
	private static final long serialVersionUID = 7464288604917584889L;

	private RegressionDataSet dataSet = null;

	public DataSetInputAndClassWrapper(RegressionDataSet ds) {
		this.dataSet = ds;
	}

	public double getDouble(long... coordinates) throws MatrixException {
		int row = (int) coordinates[ROW];
		int col = (int) coordinates[COLUMN];
		RegressionSample p = (RegressionSample) dataSet.getSample(row);
		if (p != null) {
			if (col < getSize()[COLUMN] - 1) {
				return p.getInputMatrix().getDouble(0, col);
			}
		}
		return 0.0;
	}

	public long[] getSize() {
		Sample p = dataSet.getSample(0);
		if (p == null) {
			return new long[] { 0, 0 };
		} else {
			return new long[] { dataSet.getSampleCount(), p.getInputMatrix().getColumnCount() + 1 };
		}
	}

	public void setDouble(double value, long... coordinates) {
	}

	public Object getMatrixAnnotation() {
		return "Input and Desired Class";
	}

	public RegressionDataSet getWrappedObject() {
		return dataSet;
	}

	public void setWrappedObject(RegressionDataSet object) {
		this.dataSet = object;
	}

}
