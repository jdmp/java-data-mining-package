package org.jdmp.matrix.calculation.general.statistical;

import org.apache.commons.math.stat.inference.TestUtils;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.DoubleCalculation;

public class PairedTTest extends DoubleCalculation {
	private static final long serialVersionUID = 9074733842439986005L;

	public PairedTTest(Matrix matrix) {
		super(matrix);
	}

	@Override
	public double getDouble(long... coordinates) throws MatrixException {
		try {
			long var1 = coordinates[ROW];
			long var2 = coordinates[COLUMN];
			double[] sample1 = new double[(int) getSource().getRowCount()];
			double[] sample2 = new double[(int) getSource().getRowCount()];
			for (int r = 0; r < getSource().getRowCount(); r++) {
				sample1[r] = getSource().getDouble(r, var1);
				sample2[r] = getSource().getDouble(r, var2);
			}
			double pValue = TestUtils.pairedTTest(sample1, sample2);
			return pValue;
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	@Override
	public long[] getSize() {
		return new long[] { getSource().getColumnCount(), getSource().getColumnCount() };
	}

}
