package org.jdmp.core.matrix.wrappers;

import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.core.sample.Sample;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.doublematrix.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;

public class DataSetTargetOutputMatrixWrapper extends AbstractDenseDoubleMatrix2D implements
		Wrapper<RegressionDataSet> {
	private static final long serialVersionUID = 5906451226992160036L;

	private RegressionDataSet dataSet = null;

	public DataSetTargetOutputMatrixWrapper(RegressionDataSet ds) {
		this.dataSet = ds;
	}

	public long[] getSize() {
		Sample p = dataSet.getSample(0);
		if (p == null) {
			return Coordinates.ZERO2D;
		}
		return new long[] { dataSet.getSampleCount(), p.getMatrix(Sample.TARGET).getColumnCount() };
	}

	public double getDouble(long row, long column) throws MatrixException {
		Sample p = dataSet.getSample((int) row);
		if (p != null) {
			return p.getMatrix(Sample.TARGET).getAsDouble(0, column);
		} else {
			return 0.0;
		}
	}

	public void setDouble(double value, long row, long column) throws MatrixException {
		Sample p = dataSet.getSample((int) row);
		if (p != null) {
			p.getMatrix(Sample.TARGET).setAsDouble(value, 0, column);
		}
	}

	public RegressionDataSet getWrappedObject() {
		return dataSet;
	}

	public void setWrappedObject(RegressionDataSet object) {
		this.dataSet = object;
	}

}
