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

package org.jdmp.matrix.calculation.general.misc;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.calculation.AbstractDoubleCalculation;
import org.jdmp.matrix.calculation.general.statistical.Mean;
import org.jdmp.matrix.exceptions.MatrixException;

public class Center extends AbstractDoubleCalculation {
	private static final long serialVersionUID = -2400183861312141152L;

	private Matrix mean = null;

	private boolean ignoreNaN = false;

	public Center(boolean ignoreNaN, int dimension, Matrix matrix) {
		super(dimension, matrix);
		this.ignoreNaN = ignoreNaN;
	}

	public double getDouble(long... coordinates) throws MatrixException {
		if (mean == null) {
			mean = getSource().calcNew(new Mean(getDimension(), ignoreNaN, getSource()));
		}
		switch (getDimension()) {
		case ALL:
			return getSource().getAsDouble(coordinates) - mean.getAsDouble(0, 0);
		case ROW:
			return getSource().getAsDouble(coordinates) - mean.getAsDouble(0, coordinates[COLUMN]);
		case COLUMN:
			return getSource().getAsDouble(coordinates) - mean.getAsDouble(coordinates[ROW], 0);
		}
		return Double.NaN;
	}

}
