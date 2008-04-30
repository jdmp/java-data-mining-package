package org.jdmp.matrix.calculation.entrywise.misc;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.DoubleCalculation;

public class LogisticFunction extends DoubleCalculation {
	private static final long serialVersionUID = -82780095324379021L;

	public LogisticFunction(Matrix matrix) {
		super(matrix);
	}

	public double getDouble(long... coordinates) throws MatrixException {
		return 1.0 / (Math.exp(-getSource().getDouble(coordinates)) + 1);
	}

}
