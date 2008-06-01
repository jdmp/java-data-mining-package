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

package org.jdmp.matrix.calculation.basic;

import java.util.List;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.ObjectCalculation;
import org.jdmp.matrix.util.MathUtil;
import org.jdmp.matrix.util.StringUtil;

public class Selection extends ObjectCalculation {
	private static final long serialVersionUID = 4576183558391811345L;

	private long[][] selection = null;

	public Selection(Matrix m, String selectionString) {
		super(m);
		selection = StringUtil.parseSelection(selectionString, m.getSize());
	}

	public Selection(Matrix m, List<? extends Number>... selection) {
		super(m);
		this.selection = new long[selection.length][];
		this.selection[ROW] = MathUtil.listToLong(selection[ROW]);
		this.selection[COLUMN] = MathUtil.listToLong(selection[COLUMN]);
	}

	public Selection(Matrix m, long[]... selection) {
		super(m);
		this.selection = selection;
	}

	public Object getObject(long... coordinates) throws MatrixException {
		return getSource().getObject(selection[ROW][(int) coordinates[ROW]],
				selection[COLUMN][(int) coordinates[COLUMN]]);
	}

	public void setObject(Object o, long... coordinates) throws MatrixException {
		getSource().setObject(o, selection[ROW][(int) coordinates[ROW]], selection[COLUMN][(int) coordinates[COLUMN]]);
	}

	public long[] getSize() {
		return new long[] { selection[ROW].length, selection[COLUMN].length };
	}

}
