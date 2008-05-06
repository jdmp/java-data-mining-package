package org.jdmp.matrix.calculation.general.missingvalues;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.DoubleCalculation;
import org.jdmp.matrix.util.MathUtil;

public class ImputeEM extends DoubleCalculation {
	private static final long serialVersionUID = 5633239109695608671L;

	public ImputeEM(int dimension, Matrix matrix) {
		super(dimension, matrix);
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
