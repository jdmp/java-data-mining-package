/*
 * Copyright (C) 2008-2014 by Holger Arndt
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

import java.util.List;

import org.ujmp.core.Matrix;

import cc.mallet.types.Alphabet;
import cc.mallet.types.FeatureVector;

public class Matrix2FeatureVector extends FeatureVector {
	private static final long serialVersionUID = -6089598476246128396L;

	public Matrix2FeatureVector(Matrix m, Alphabet alphabet, List<Integer> cumSum) {
		super(alphabet, new double[] { 0 });

		int count = 0;
		for (long[] c : m.availableCoordinates()) {
			if (m.getAsDouble(c) != 0) {
				count++;
			}
		}
		indices = new int[count];
		values = new double[count];

		if (m.getRowCount() == 1 && m.getColumnCount() != 1) {
			int i = 0;
			for (long[] c : m.availableCoordinates()) {
				double v = m.getAsDouble(c);
				if (v != 0) {
					int offset = cumSum.get((int) c[Matrix.COLUMN]);
					int next = cumSum.get((int) c[Matrix.COLUMN] + 1);
					int pos = (int) (offset + v - 1);
					if (pos < next) {
						indices[i] = pos;
						values[i] = 1;
						i++;
					}
				}
			}
		} else if (m.getRowCount() != 1 && m.getColumnCount() == 1) {
			int i = 0;
			for (long[] c : m.availableCoordinates()) {
				double v = m.getAsDouble(c);
				if (v != 0) {
					int offset = cumSum.get((int) c[Matrix.ROW]);
					int next = cumSum.get((int) c[Matrix.ROW] + 1);
					int pos = (int) (offset + v);
					if (pos < next) {
						indices[i] = pos;
						values[i] = 1;
						i++;
					}
				}
			}
		} else {
			throw new RuntimeException("cannot convert Matrix to Vector");
		}
	}
}
