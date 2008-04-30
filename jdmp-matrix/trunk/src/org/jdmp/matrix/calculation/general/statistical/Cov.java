package org.jdmp.matrix.calculation.general.statistical;

import java.util.logging.Level;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.DoubleCalculation;
import org.jdmp.matrix.util.MathUtil;

public class Cov extends DoubleCalculation {
	private static final long serialVersionUID = -2100820298353936855L;

	private Matrix mean = null;

	private boolean ignoreNaN = false;

	public Cov(boolean ignoreNaN, Matrix matrix) {
		super(matrix);
		this.ignoreNaN = ignoreNaN;
	}

	@Override
	public double getDouble(long... coordinates) throws MatrixException {
		double sumProd = 0.0;
		long rows = getSource().getRowCount();
		long N = 0;
		double deltaX = 0.0;
		double deltaY = 0.0;

		if (mean == null) {
			try {
				mean = new Mean(ROW, ignoreNaN, getSource()).calc(Ret.NEW);
			} catch (MatrixException e) {
				logger.log(Level.WARNING, "could not calculate Matrix", e);
			}
		}

		if (ignoreNaN) {

			for (int i = 0; i < rows; i++) {
				deltaX = getSource().getDouble(i, coordinates[ROW]) - mean.getDouble(0, coordinates[ROW]);
				deltaY = getSource().getDouble(i, coordinates[COLUMN]) - mean.getDouble(0, coordinates[COLUMN]);

				if (!MathUtil.isNaNOrInfinite(deltaX) && !MathUtil.isNaNOrInfinite(deltaY)) {
					N++;
					sumProd += deltaX * deltaY;
				}
			}

		} else {

			N = rows;
			for (int i = 0; i < rows; i++) {
				deltaX = getSource().getDouble(i, coordinates[ROW]) - mean.getDouble(0, coordinates[ROW]);
				deltaY = getSource().getDouble(i, coordinates[COLUMN]) - mean.getDouble(0, coordinates[COLUMN]);
				sumProd += deltaX * deltaY;
			}

		}

		double cov = sumProd / (N - 1);

		return cov;
	}

	@Override
	public long[] getSize() {
		return new long[] { getSource().getColumnCount(), getSource().getColumnCount() };
	}

}
