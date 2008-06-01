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

import java.util.Arrays;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.Matrix.EntryType;
import org.jdmp.matrix.calculation.DoubleCalculation;
import org.jdmp.matrix.coordinates.Coordinates;
import org.jdmp.matrix.exceptions.MatrixException;

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
