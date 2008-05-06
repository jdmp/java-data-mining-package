package org.jdmp.matrix.calculation.entrywise.rounding;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.calculation.DoubleCalculation;

public class Round extends DoubleCalculation {
	private static final long serialVersionUID = -5038322249059783563L;

	public Round(Matrix matrix) {
		super(matrix);
	}

	@Override
	public double getDouble(long... coordinates) throws MatrixException {
		return Math.round(getSource().getDouble(coordinates));
	}

	public static Matrix calcNew(Matrix source) throws MatrixException {
		Matrix ret = MatrixFactory.zeros(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			ret.setDouble(Math.round(source.getDouble(c)), c);
		}
		return ret;
	}

}
