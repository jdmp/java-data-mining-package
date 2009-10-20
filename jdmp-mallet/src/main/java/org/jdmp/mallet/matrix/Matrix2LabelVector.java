/*
 * Copyright (C) 2008-2009 by Holger Arndt
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

package org.jdmp.mallet.matrix;

import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;

import cc.mallet.types.LabelAlphabet;
import cc.mallet.types.LabelVector;

public class Matrix2LabelVector extends LabelVector {
	private static final long serialVersionUID = -2049126562347727714L;

	public Matrix2LabelVector(Matrix m, LabelAlphabet alphabet) {
		super(alphabet, new double[] { 0 });

		int count = 0;
		for (long[] c : m.availableCoordinates()) {
			if (m.getAsDouble(c) != 0) {
				count++;
			}
		}

		if (m.getRowCount() == 1 && m.getColumnCount() != 1) {
			indices = new int[count];
			values = new double[count];
			int i = 0;
			for (long[] c : m.availableCoordinates()) {
				double v = m.getAsDouble(c);
				if (v != 0) {
					indices[i] = (int) c[Matrix.COLUMN];
					values[i] = v;
					i++;
				}
			}
		} else if (m.getRowCount() != 1 && m.getColumnCount() == 1) {
			indices = new int[count];
			values = new double[count];
			int i = 0;
			for (long[] c : m.availableCoordinates()) {
				double v = m.getAsDouble(c);
				if (v != 0) {
					indices[i] = (int) c[Matrix.ROW];
					values[i] = m.getAsDouble(c);
					i++;
				}
			}
		} else if (m.isScalar()) {
			indices = new int[] { 0, 1 };
			values = new double[] { m.getAsDouble(0, 0), 1 - m.getAsDouble(0, 0) };
		} else {
			throw new MatrixException("cannot convert Matrix to Vector");
		}
	}
}
