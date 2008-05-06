package org.jdmp.matrix.calculation.basic;

import java.util.logging.Level;

import org.jdmp.matrix.Coordinates;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.DoubleCalculation;
import org.jdmp.matrix.implementations.basic.DefaultDenseDoubleMatrix2D;
import org.jdmp.matrix.util.MathUtil;

public class Mtimes extends DoubleCalculation {
	private static final long serialVersionUID = 4170937261358240120L;

	private boolean ignoreNaN = false;

	public Mtimes(boolean ignoreNaN, Matrix m1, Matrix m2) {
		super(m1, m2);
		this.ignoreNaN = ignoreNaN;
	}

	@Override
	public double getDouble(long... coordinates) throws MatrixException {
		return ignoreNaN ? MathUtil.ignoreNaN(getSources()[0].getDouble(coordinates))
				* MathUtil.ignoreNaN(getSources()[1].getDouble(coordinates)) : getSources()[0].getDouble(coordinates)
				* getSources()[1].getDouble(coordinates);
	}

	public static Matrix calc(boolean ignoreNaN, Matrix m1, Matrix m2) throws MatrixException {
		if (m1.getColumnCount() != m2.getRowCount()) {
			logger.log(Level.WARNING, "matrices have wrong size: " + Coordinates.toString(m1.getSize()) + " and "
					+ Coordinates.toString(m2.getSize()), new Exception());
			return null;
		}

		int i, j, k;
		double sum;
		double[][] ret = new double[(int) m1.getRowCount()][(int) m2.getColumnCount()];

		if (ignoreNaN) {
			for (i = (int) m1.getRowCount(); --i >= 0;) {
				for (j = ret[0].length; --j >= 0;) {
					sum = 0.0;
					for (k = (int) m1.getColumnCount(); --k >= 0;) {
						sum += MathUtil.ignoreNaN(m1.getDouble(i, k)) * MathUtil.ignoreNaN(m2.getDouble(k, j));
					}
					ret[i][j] = sum;
				}
			}
		} else {
			for (i = (int) m1.getRowCount(); --i >= 0;) {
				for (j = ret[0].length; --j >= 0;) {
					sum = 0.0;
					for (k = (int) m1.getColumnCount(); --k >= 0;) {
						sum += m1.getDouble(i, k) * m2.getDouble(k, j);
					}
					ret[i][j] = sum;
				}
			}
		}

		return new DefaultDenseDoubleMatrix2D(ret);
	}

}
