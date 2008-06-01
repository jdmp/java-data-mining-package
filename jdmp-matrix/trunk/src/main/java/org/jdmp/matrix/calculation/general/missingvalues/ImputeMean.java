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

package org.jdmp.matrix.calculation.general.missingvalues;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.DoubleCalculation;
import org.jdmp.matrix.calculation.general.statistical.Mean;
import org.jdmp.matrix.util.MathUtil;

public class ImputeMean extends DoubleCalculation {
	private static final long serialVersionUID = -3749987323095497386L;

	private Matrix mean = null;

	public ImputeMean(int dimension, Matrix matrix) {
		super(dimension, matrix);
	}

	public double getDouble(long... coordinates) throws MatrixException {
		if (mean == null) {
			mean = getSource().calcNew(new Mean(getDimension(), true, getSource()));
		}
		double v = getSource().getDouble(coordinates);
		if (MathUtil.isNaNOrInfinite(v)) {
			switch (getDimension()) {
			case ALL:
				return mean.getDouble(0, 0);
			case ROW:
				return mean.getDouble(0, coordinates[COLUMN]);
			case COLUMN:
				return mean.getDouble(coordinates[ROW], 0);
			}
		} else {
			return v;
		}
		return 0.0;
	}

}
