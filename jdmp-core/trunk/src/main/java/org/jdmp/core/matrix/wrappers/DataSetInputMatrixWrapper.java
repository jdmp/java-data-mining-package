package org.jdmp.core.matrix.wrappers;

import org.jdmp.core.algorithm.regression.Regressor;
import org.jdmp.core.dataset.BasicDataSet;
import org.jdmp.core.sample.Sample;
import org.ujmp.core.Matrix;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.doublematrix.AbstractDenseDoubleMatrix2D;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;

public class DataSetInputMatrixWrapper extends AbstractDenseDoubleMatrix2D implements
		Wrapper<BasicDataSet> {
	private static final long serialVersionUID = -817570097594349208L;

	public static final Object INPUT = Regressor.INPUT;

	private BasicDataSet dataSet = null;

	public DataSetInputMatrixWrapper(BasicDataSet ds) {
		this.dataSet = ds;
	}

	public long[] getSize() {
		Sample p = dataSet.getSample(0);
		if (p == null) {
			return Coordinates.ZERO2D;
		}
		Matrix input = p.getMatrix(INPUT);
		if (input != null) {
			return new long[] { dataSet.getSampleCount(), input.getValueCount() };
		} else {
			return Coordinates.ZERO2D;
		}

	}

	public double getDouble(long row, long column) throws MatrixException {
		Sample p = dataSet.getSample((int) row);
		if (p != null) {
			if (p.getMatrix(INPUT) != null) {
				long r = column / p.getMatrix(INPUT).getColumnCount();
				long c = column % p.getMatrix(INPUT).getColumnCount();
				return p.getMatrix(INPUT).getAsDouble(r, c);
			}
		}
		return 0.0;
	}

	public void setDouble(double value, long row, long column) throws MatrixException {
		Sample p = dataSet.getSample((int) row);
		if (p != null) {
			if (p.getMatrix(INPUT) != null) {
				long r = column / p.getMatrix(INPUT).getColumnCount();
				long c = column % p.getMatrix(INPUT).getColumnCount();
				p.getMatrix(INPUT).setAsDouble(value, r, c);
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
