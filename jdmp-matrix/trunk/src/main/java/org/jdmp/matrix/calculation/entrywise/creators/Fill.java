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
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.Matrix.EntryType;
import org.jdmp.matrix.calculation.ObjectCalculation;

public class Fill extends ObjectCalculation {
	private static final long serialVersionUID = -3477957135967841340L;

	private Object fill = null;

	public Fill(Matrix matrix, Object value) {
		super(matrix);
		this.fill = value;
	}

	@Override
	public Object getObject(long... coordinates) {
		return fill;
	}

	public static Matrix calc(Matrix source, Object fill) throws MatrixException {
		Matrix ret = MatrixFactory.zeros(source.getEntryType(), source.getSize());
		for (long[] c : source.allCoordinates()) {
			ret.setObject(fill, c);
		}
		return ret;
	}

	public static Matrix calc(Object fill, long... size) throws MatrixException {
		Matrix ret = null;
		if (fill instanceof Number) {
			ret = MatrixFactory.zeros(EntryType.DOUBLE, size);
		} else if (fill instanceof String) {
			ret = MatrixFactory.zeros(EntryType.STRING, size);
		} else {
			ret = MatrixFactory.zeros(EntryType.OBJECT, size);
		}
		for (long[] c : ret.allCoordinates()) {
			ret.setObject(fill, c);
		}
		return ret;
	}
}
