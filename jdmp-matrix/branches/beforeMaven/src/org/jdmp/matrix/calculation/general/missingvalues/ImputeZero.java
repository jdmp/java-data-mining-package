package org.jdmp.matrix.calculation.general.missingvalues;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.DoubleCalculation;
import org.jdmp.matrix.util.MathUtil;

public class ImputeZero extends DoubleCalculation {
	private static final long serialVersionUID = 7347927669886417833L;

	public ImputeZero(Matrix matrix) {
		super(matrix);
	}

	public double getDouble(long... coordinates) throws MatrixException {
		double v = getSource().getDouble(coordinates);
		if (MathUtil.isNaNOrInfinite(v)) {
			return 0.0;
		} else {
			return v;
		}
	}

}
