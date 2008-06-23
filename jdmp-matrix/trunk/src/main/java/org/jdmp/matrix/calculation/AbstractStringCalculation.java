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

package org.jdmp.matrix.calculation;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.Matrix.EntryType;
import org.jdmp.matrix.coordinates.Coordinates;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.util.MathUtil;

public abstract class AbstractStringCalculation extends AbstractGenericCalculation<String> {

	public AbstractStringCalculation(Matrix... sources) {
		super(sources);
	}

	public AbstractStringCalculation(int dimension, Matrix... sources) {
		super(dimension, sources);
	}

	public double getDouble(long... coordinates) throws MatrixException {
		return MathUtil.getDouble(getString(coordinates));
	}

	public String getObject(long... coordinates) throws MatrixException {
		return getString(coordinates);
	}

	public void setDouble(double value, long... coordinates) throws MatrixException {
	}

	public void setObject(Object value, long... coordinates) throws MatrixException {
	}

	public void setString(String value, long... coordinates) throws MatrixException {
	}

	public final Matrix calcOrig() throws MatrixException {
		if (!Coordinates.equals(getSource().getSize(), getSize())) {
			throw new MatrixException(
					"Cannot change Matrix size. Use calc(Ret.NEW) or calc(Ret.LINK) instead.");
		}
		for (long[] c : getSource().allCoordinates()) {
			getSource().setAsString(getString(c), c);
		}
		return getSource();
	}

	public final Matrix calcNew() throws MatrixException {
		Matrix result = MatrixFactory.zeros(getEntryType(), getSize());
		// TODO: copy annotation
		for (long[] c : result.allCoordinates()) {
			result.setAsString(getString(c), c);
		}
		return result;
	}

	public EntryType getEntryType() {
		return EntryType.STRING;
	}

}
