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
import org.jdmp.matrix.calculation.AbstractDoubleCalculation;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.util.MathUtil;

public class ImputeKNN extends AbstractDoubleCalculation {
	private static final long serialVersionUID = -4923873199518001578L;

	private Matrix sourceCopy = null;

	public ImputeKNN(int dimension, Matrix matrix) {
		super(dimension, matrix);
	}

	public Matrix getNearestNeighbor(long... coordinates) throws MatrixException {
		double bestDistance = Double.MAX_VALUE;
		Matrix bestMatrix = null;
		Matrix toReplace = null;
		switch (getDimension()) {
		case ROW:
			toReplace = getSource().selectRows(Ret.LINK, coordinates[ROW]);
			for (long r = getSource().getRowCount() - 1; r != -1; r--) {
				if (r != coordinates[ROW]) {
					Matrix candidate = getSource().selectRows(Ret.LINK, r);
					if (!MathUtil.isNaNOrInfinite(candidate.getAsDouble(0, coordinates[COLUMN]))) {
						double distance = toReplace.euklideanDistanceTo(candidate, true);
						if (distance < bestDistance) {
							bestDistance = distance;
							bestMatrix = candidate;
						}
					}
				}
			}
			break;
		case COLUMN:
			toReplace = getSource().selectColumns(Ret.LINK, coordinates[COLUMN]);
			for (long c = getSource().getColumnCount() - 1; c != -1; c--) {
				if (c != coordinates[COLUMN]) {
					Matrix candidate = getSource().selectColumns(Ret.LINK, c);
					double distance = toReplace.euklideanDistanceTo(candidate, true);
					if (distance < bestDistance) {
						bestDistance = distance;
						bestMatrix = candidate;
					}
				}
			}
			break;
		}
		return bestMatrix;
	}

	public double getDouble(long... coordinates) throws MatrixException {
		if (sourceCopy == null) {
			sourceCopy = getSource().clone();
		}
		double v = sourceCopy.getAsDouble(coordinates);
		if (MathUtil.isNaNOrInfinite(v)) {
			switch (getDimension()) {
			case ROW:
				return getNearestNeighbor(coordinates).getAsDouble(0, coordinates[COLUMN]);
			case COLUMN:
				return getNearestNeighbor(coordinates).getAsDouble(coordinates[ROW], 0);
			}
		} else {
			return v;
		}
		return 0.0;
	}

}
