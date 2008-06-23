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

package org.jdmp.matrix.calculation.general.solving;

import java.lang.reflect.Constructor;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.calculation.AbstractDoubleCalculation;
import org.jdmp.matrix.coordinates.Coordinates;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.implementations.misc.CommonsMathRealMatrix;

public class Inv extends AbstractDoubleCalculation {
	private static final long serialVersionUID = 7886298456216056038L;

	private Matrix inv = null;

	public Inv(Matrix matrix) {
		super(matrix);
	}

	@Override
	public double getDouble(long... coordinates) throws MatrixException {
		if (inv == null) {
			try {

				Matrix m = getSource();

				try {
					Class<?> c = Class.forName("org.jdmp.mtj.MTJDenseDoubleMatrix2D");
					Constructor<?> con = c.getConstructor(Matrix.class);
					m = (Matrix) con.newInstance(m);
				} catch (ClassNotFoundException e) {
					m = new CommonsMathRealMatrix(m);
				}

				inv = m.inv();

			} catch (Exception e) {
				throw new MatrixException(e);
			}
		}
		return inv.getAsDouble(coordinates);
	}

	@Override
	public long[] getSize() {
		return Coordinates.transpose(getSource().getSize());
	}

}
