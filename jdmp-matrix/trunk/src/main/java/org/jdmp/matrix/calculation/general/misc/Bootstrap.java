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

import java.util.ArrayList;
import java.util.List;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.ObjectCalculation;
import org.jdmp.matrix.util.MathUtil;

public class Bootstrap extends ObjectCalculation {
	private static final long serialVersionUID = -5084329826465538416L;

	private int count = 0;

	private Matrix selection = null;

	public Bootstrap(Matrix m) {
		this(m, (int) m.getRowCount());
	}

	public Bootstrap(Matrix m, int count) {
		super(m);
		this.count = count;
	}

	@Override
	public Object getObject(long... coordinates) throws MatrixException {
		if (selection == null) {
			List<Integer> rows = new ArrayList<Integer>();
			for (int i = 0; i < count; i++) {
				int s = MathUtil.nextInteger(0, (int) getSource().getRowCount() - 1);
				rows.add(s);
			}
			selection = getSource().selectRows(Ret.LINK, rows);
		}
		return selection.getObject(coordinates);
	}

}
