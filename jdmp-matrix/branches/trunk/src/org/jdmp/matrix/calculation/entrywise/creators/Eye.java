package org.jdmp.matrix.calculation.entrywise.creators;

import java.util.Arrays;

import org.jdmp.matrix.Coordinates;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.Matrix.EntryType;
import org.jdmp.matrix.calculation.DoubleCalculation;

public class Eye extends DoubleCalculation {
	private static final long serialVersionUID = 2547827499345834225L;

	public Eye(Matrix matrix) {
		super(matrix);
	}

	@Override
	public double getDouble(long... coordinates) {
		return coordinates[ROW] == coordinates[COLUMN] ? 1.0 : 0.0;
	}

	public static Matrix calc(Matrix source) throws MatrixException {
		Matrix ret = MatrixFactory.zeros(source.getSize());
		long[] c = Coordinates.copyOf(source.getSize());
		for (int i = 0; Coordinates.isSmallerThan(c, source.getSize()); i++) {
			Arrays.fill(c, i);
			ret.setDouble(1.0, c);
		}
		return ret;
	}

	public static Matrix calc(long... size) throws MatrixException {
		return calc(EntryType.DOUBLE, size);
	}

	public static Matrix calc(EntryType entryType, long... size) throws MatrixException {
		Matrix ret = MatrixFactory.zeros(entryType, size);
		long[] c = Coordinates.copyOf(size);
		for (int i = 0; Coordinates.isSmallerThan(c, size); i++) {
			Arrays.fill(c, i);
			ret.setDouble(1.0, c);
		}
		return ret;
	}
}
