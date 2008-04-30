package org.jdmp.matrix.calculation.entrywise.creators;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.Matrix.EntryType;
import org.jdmp.matrix.calculation.DoubleCalculation;

public class Ones extends DoubleCalculation {
	private static final long serialVersionUID = 2547827499345834225L;

	public Ones(Matrix matrix) {
		super(matrix);
	}

	@Override
	public double getDouble(long... coordinates) {
		return 1.0;
	}

	public static Matrix calc(Matrix source) throws MatrixException {
		Matrix ret = Matrix.zeros(source.getSize());
		for (long[] c : source.allCoordinates()) {
			ret.setDouble(1.0, c);
		}
		return ret;
	}

	public static Matrix calc(long... size) throws MatrixException {
		return calc(EntryType.DOUBLE, size);
	}

	public static Matrix calc(EntryType entryType, long... size) throws MatrixException {
		Matrix ret = Matrix.zeros(entryType, size);
		for (long[] c : ret.allCoordinates()) {
			ret.setDouble(1.0, c);
		}
		return ret;
	}
}
