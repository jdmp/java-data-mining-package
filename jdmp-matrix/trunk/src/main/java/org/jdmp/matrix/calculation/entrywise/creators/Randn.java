/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
 *
 * This file is part of the Java Data Mining Package (JDMP).
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership and licensing.
 *
 * JDMP is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * JDMP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with JDMP; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.jdmp.matrix.calculation.entrywise.creators;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.Matrix.EntryType;
import org.jdmp.matrix.calculation.DoubleCalculation;
import org.jdmp.matrix.exceptions.MatrixException;
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
