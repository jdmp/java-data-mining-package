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

import java.util.Collection;
import java.util.List;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.calculation.AbstractObjectCalculation;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.util.MathUtil;
import org.jdmp.matrix.util.StringUtil;

public class Deletion extends AbstractObjectCalculation {
	private static final long serialVersionUID = 3714270132906708701L;

	private long[][] selection = null;

	public Deletion(Matrix m, String deletionString) {
		this(m, StringUtil.parseSelection(deletionString, m.getSize()));
	}

	public Deletion(Matrix m, Collection<? extends Number>... deletion) {
		super(m);

		List<Long> rows = MathUtil.sequenceListLong(0, getSource().getRowCount() - 1);
		List<Long> columns = MathUtil.sequenceListLong(0, getSource().getColumnCount() - 1);

		for (Number n : deletion[ROW]) {
			rows.remove((Long) n.longValue());
		}

		for (Number n : deletion[COLUMN]) {
			columns.remove((Long) n.longValue());
		}

		selection = new long[2][];
		selection[ROW] = MathUtil.collectionToLong(rows);
		selection[COLUMN] = MathUtil.collectionToLong(columns);
	}

	public Deletion(Matrix m, long[]... deletion) {
		super(m);

		List<Long> rows = MathUtil.sequenceListLong(0, getSource().getRowCount() - 1);
		List<Long> columns = MathUtil.sequenceListLong(0, getSource().getColumnCount() - 1);

		for (int r = 0; r < deletion[ROW].length; r++) {
			rows.remove(deletion[ROW][r]);
		}

		for (int c = 0; c < deletion[COLUMN].length; c++) {
			columns.remove(deletion[COLUMN][c]);
		}

		selection = new long[2][];
		selection[ROW] = MathUtil.collectionToLong(rows);
		selection[COLUMN] = MathUtil.collectionToLong(columns);
	}

	public Object getObject(long... coordinates) throws MatrixException {
		return getSource().getObject(selection[ROW][(int) coordinates[ROW]],
				selection[COLUMN][(int) coordinates[COLUMN]]);
	}

	public void setObject(Object o, long... coordinates) throws MatrixException {
		getSource().setObject(o, selection[ROW][(int) coordinates[ROW]],
				selection[COLUMN][(int) coordinates[COLUMN]]);
	}

	public long[] getSize() {
		return new long[] { selection[ROW].length, selection[COLUMN].length };
	}

}
