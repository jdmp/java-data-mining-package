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
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.DoubleCalculation;
import org.jdmp.matrix.calculation.general.statistical.Std;

public class Standardize extends DoubleCalculation {
	private static final long serialVersionUID = 6454174968175712888L;

	private Matrix center = null;

	private Matrix sigma = null;

	private boolean ignoreNaN = false;

	public Standardize(boolean ignoreNaN, int dimension, Matrix matrix) {
		super(dimension, matrix);
		this.ignoreNaN = ignoreNaN;
	}

	public double getDouble(long... coordinates) throws MatrixException {
		if (center == null) {
			center = getSource().calcNew(new Center(ignoreNaN, getDimension(), getSource()));
		}
		if (sigma == null) {
			sigma = center.calcNew(new Std(getDimension(), ignoreNaN, center));
		}
		switch (getDimension()) {
		case ALL:
			return center.getDouble(coordinates) / sigma.getDouble(0, 0);
		case ROW:
			return center.getDouble(coordinates) / sigma.getDouble(0, coordinates[COLUMN]);
		case COLUMN:
			return center.getDouble(coordinates) / sigma.getDouble(coordinates[ROW], 0);
		}
		return Double.NaN;
	}

}
