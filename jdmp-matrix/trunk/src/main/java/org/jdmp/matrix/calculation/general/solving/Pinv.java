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

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.calculation.AbstractDoubleCalculation;
import org.jdmp.matrix.coordinates.Coordinates;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.util.JDMPSettings;

public class Pinv extends AbstractDoubleCalculation {
	private static final long serialVersionUID = 7886298456216056038L;

	private Matrix pinv = null;

	public Pinv(Matrix matrix) {
		super(matrix);
	}

	@Override
	public double getDouble(long... coordinates) throws MatrixException {
		if (pinv == null) {

			Matrix[] ms = getSource().svd();
			Matrix u = ms[0];
			Matrix s = ms[1];
			Matrix v = ms[2];

			for (int i = (int) Math.min(s.getRowCount(), s.getColumnCount()); --i >= 0;) {
				double d = s.getAsDouble(i, i);
				if (Math.abs(d) > JDMPSettings.getTolerance()) {
					s.setAsDouble(1.0 / d, i, i);
				}
			}

			pinv = v.mtimes(s.transpose()).mtimes(u.transpose());

		}
		return pinv.getAsDouble(coordinates);
	}

	@Override
	public long[] getSize() {
		return Coordinates.transpose(getSource().getSize());
	}

}
