package org.jdmp.matrix.calculation.entrywise.creators;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.Matrix.EntryType;
import org.jdmp.matrix.calculation.DoubleCalculation;
import org.jdmp.matrix.util.MathUtil;

public class Randn extends DoubleCalculation {
	private static final long serialVersionUID = 3846626738610954086L;

	private double mean = 0.0;

	private double sigma = 1.0;

	public Randn(Matrix matrix) {
		super(matrix);
	}

	public Randn(Matrix matrix, double mean, double sigma) {
		super(matrix);
		this.mean = mean;
		this.sigma = sigma;
	}

	@Override
	public double getDouble(long... coordinates) {
		return MathUtil.nextGaussian(mean, sigma);
	}

	public static Matrix calc(long... size) throws MatrixException {
		return calc(EntryType.DOUBLE, size);
	}

	public static Matrix calc(Matrix source) throws MatrixException {
		return calc(source, 0.0, 1.0);
	}

	public static Matrix calc(Matrix source, double mean, double sigma) throws MatrixException {
		Matrix ret = MatrixFactory.zeros(source.getSize());
		for (long[] c : source.allCoordinates()) {
			ret.setDouble(MathUtil.nextGaussian(mean, sigma), c);
		}
		return ret;
	}

	public static Matrix calc(EntryType entryType, long... size) throws MatrixException {
		Matrix ret = MatrixFactory.zeros(entryType, size);
		for (long[] c : ret.allCoordinates()) {
			ret.setDouble(MathUtil.nextGaussian(0.0, 1.0), c);
		}
		return ret;
	}

}
