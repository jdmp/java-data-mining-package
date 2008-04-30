package org.jdmp.matrix.calculation.entrywise.creators;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.Matrix.EntryType;
import org.jdmp.matrix.calculation.DoubleCalculation;
import org.jdmp.matrix.util.MathUtil;

public class Rand extends DoubleCalculation {
	private static final long serialVersionUID = 1482175899706574542L;

	private double min = 0.0;

	private double max = 1.0;

	public Rand(Matrix matrix) {
		super(matrix);
	}

	public Rand(Matrix matrix, double min, double max) {
		super(matrix);
		this.min = min;
		this.max = max;
	}

	@Override
	public double getDouble(long... coordinates) {
		return MathUtil.nextUniform(min, max);
	}

	public static Matrix calc(long... size) throws MatrixException {
		return calc(EntryType.DOUBLE, size);
	}

	public static Matrix calc(Matrix source) throws MatrixException {
		return calc(source, 0.0, 1.0);
	}

	public static Matrix calc(Matrix source, double min, double max) throws MatrixException {
		Matrix ret = Matrix.zeros(source.getSize());
		for (long[] c : source.allCoordinates()) {
			ret.setDouble(MathUtil.nextUniform(min, max), c);
		}
		return ret;
	}

	public static Matrix calc(EntryType entryType, long... size) throws MatrixException {
		Matrix ret = Matrix.zeros(entryType, size);
		for (long[] c : ret.allCoordinates()) {
			ret.setDouble(MathUtil.nextUniform(0.0, 1.0), c);
		}
		return ret;
	}

}
