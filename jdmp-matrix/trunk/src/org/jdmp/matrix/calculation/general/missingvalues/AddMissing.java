package org.jdmp.matrix.calculation.general.missingvalues;

import java.util.Arrays;

import org.jdmp.matrix.Coordinates;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.DoubleCalculation;
import org.jdmp.matrix.util.MathUtil;

public class AddMissing extends DoubleCalculation {
	private static final long serialVersionUID = -8211102877475985639L;

	private double[] percentMissing = null;

	private Matrix missingValues = null;

	public AddMissing(int dimension, Matrix source, double... percentMissing) {
		super(dimension, source);
		if (percentMissing.length == 1 && dimension != ALL) {
			this.percentMissing = new double[(int) getSource().getSize(dimension)];
			Arrays.fill(this.percentMissing, percentMissing[0]);
		} else {
			this.percentMissing = percentMissing;
		}
	}

	public double getDouble(long... coordinates) throws MatrixException {
		if (missingValues == null) {

			missingValues = Matrix.sparse(getSource().getSize());

			switch (getDimension()) {
			case ALL:
				int count = (int) (percentMissing[0] * Coordinates.product(getSource().getSize()));
				for (int i = 0; i < count; i++) {
					double v = 0.0;
					int r = 0;
					int c = 0;
					do {
						r = MathUtil.nextInteger(0, (int) getSource().getRowCount() - 1);
						c = MathUtil.nextInteger(0, (int) getSource().getColumnCount() - 1);
						v = missingValues.getDouble(r, c);
					} while (MathUtil.isNaNOrInfinite(v));
					missingValues.setDouble(Double.NaN, r, c);
				}
				break;
			case COLUMN:
				int missingCount = (int) (getSource().getColumnCount() * percentMissing[0]);
				for (long r = getSource().getRowCount() - 1; r != -1; r--) {
					for (int i = 0; i < missingCount; i++) {
						double v = 0.0;
						int c = 0;
						do {
							c = MathUtil.nextInteger(0, (int) getSource().getColumnCount() - 1);
							v = missingValues.getDouble(r, c);
						} while (MathUtil.isNaNOrInfinite(v));
						missingValues.setDouble(Double.NaN, r, c);
					}
				}
			}
		}
		if (missingValues.contains(coordinates)) {
			return Double.NaN;
		} else {
			return getSource().getDouble(coordinates);
		}
	}

	public void setDouble(double value, long... coordinates) throws MatrixException {
		getSource().setDouble(value, coordinates);
	}

}
