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

package org.jdmp.matrix.implementations.basic;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.stubs.AbstractDenseStringMatrix2D;
import org.jdmp.matrix.util.StringUtil;

public class DefaultDenseStringMatrix2D extends AbstractDenseStringMatrix2D {
	private static final long serialVersionUID = -4292004796378125964L;

	private String[][] values = null;

	public DefaultDenseStringMatrix2D(String string) {
		string = string.replaceAll(StringUtil.BRACKETS, "");
		String[] rows = string.split(StringUtil.SEMICOLONORNEWLINE);
		String[] cols = rows[0].split(StringUtil.COLONORSPACES);
		values = new String[rows.length][cols.length];
		for (int r = 0; r < rows.length; r++) {
			cols = rows[r].split(StringUtil.COLONORSPACES);
			for (int c = 0; c < cols.length; c++) {
				values[r][c] = cols[c];
			}
		}
	}

	public DefaultDenseStringMatrix2D(Matrix source) throws MatrixException {
		this(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			setString(source.getString(c), c);
		}
	}

	public DefaultDenseStringMatrix2D(long... size) {
		values = new String[(int) size[ROW]][(int) size[COLUMN]];
	}

	public DefaultDenseStringMatrix2D(String[]... values) {
		this.values = values;
	}

	public long[] getSize() {
		return new long[] { values.length, values[0].length };
	}

	@Override
	public long getRowCount() {
		return values.length;
	}

	@Override
	public long getColumnCount() {
		return values[0].length;
	}

	public String getString(long... coordinates) {
		return values[(int) coordinates[ROW]][(int) coordinates[COLUMN]];
	}

	public void setString(String value, long... coordinates) {
		values[(int) coordinates[ROW]][(int) coordinates[COLUMN]] = value;
	}	

}