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

package org.jdmp.matrix.calculation.entrywise.basic;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.calculation.AbstractDoubleCalculation;
import org.jdmp.matrix.exceptions.MatrixException;

public class Power extends AbstractDoubleCalculation {
	private static final long serialVersionUID = -6766560469728046231L;

	public Power(Matrix matrix, Matrix power) {
		super(matrix, power);
	}

	public Power(Matrix m1, double v2) throws MatrixException {
		super(m1, MatrixFactory.fill(v2, m1.getSize()));
	}

	@Override
	public double getDouble(long... coordinates) throws MatrixException {
		return Math.pow(getSource().getAsDouble(coordinates), getSources()[1].getAsDouble(coordinates));
	}

	public static Matrix calc(Matrix source, Matrix power) throws MatrixException {
		Matrix ret = MatrixFactory.zeros(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			ret.setAsDouble(Math.pow(source.getAsDouble(c), power.getAsDouble(c)), c);
		}
		return ret;
	}

	public static Matrix calc(Matrix source, double power) throws MatrixException {
		Matrix ret = MatrixFactory.zeros(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			ret.setAsDouble(Math.pow(source.getAsDouble(c), power), c);
		}
		return ret;
	}

}
