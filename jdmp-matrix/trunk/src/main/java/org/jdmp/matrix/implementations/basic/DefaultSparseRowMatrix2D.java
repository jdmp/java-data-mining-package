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

import java.util.HashMap;
import java.util.Map;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.coordinates.CoordinateIterator2D;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.stubs.AbstractSparseGenericMatrix;
import org.jdmp.matrix.util.MathUtil;

public class DefaultSparseRowMatrix2D<A> extends AbstractSparseGenericMatrix<A> {
	private static final long serialVersionUID = -5291604525500706427L;

	private long[] size = new long[] { 1, 1 };

	private Map<Long, Matrix> rows = new HashMap<Long, Matrix>();

	public DefaultSparseRowMatrix2D(long... size) {
		this.size = size;
	}

	public DefaultSparseRowMatrix2D(Matrix m) {
		this.size = m.getSize();
		for (long[] c : m.availableCoordinates()) {
			setObject(m.getObject(c), c);
		}
	}

	@Override
	public A getObject(long... coordinates) throws MatrixException {
		Matrix m = rows.get(coordinates[ROW]);
		if (m == null) {
			return null;
		} else {
			return (A) m.getObject(0, coordinates[COLUMN]);
		}
	}

	@Override
	public Iterable<long[]> allCoordinates() {
		return new CoordinateIterator2D(getSize());
	}

	@Override
	public boolean contains(long... coordinates) {
		return getObject(coordinates) != null;
	}

	@Override
	public double getDouble(long... coordinates) throws MatrixException {
		return MathUtil.getDouble(getObject(coordinates));
	}

	@Override
	public void setDouble(double value, long... coordinates) throws MatrixException {
		setObject(value, coordinates);
	}

	@Override
	public void setObject(Object o, long... coordinates) throws MatrixException {
		Matrix m = rows.get(coordinates[ROW]);
		if (m == null) {
			m = new DefaultSparseGenericMatrix<A>(1l, getColumnCount());
			rows.put(coordinates[ROW], m);
		}
		m.setObject(o, 0, coordinates[COLUMN]);
	}

	@Override
	public EntryType getEntryType() {
		return EntryType.GENERIC;
	}

	@Override
	public long[] getSize() {
		return size;
	}

	public void setSize(long... size) {
		if (this.size[COLUMN] != size[COLUMN]) {
			for (Matrix m : rows.values()) {
				m.setSize(1, size[COLUMN]);
			}
		}
		this.size = size;
	}

	public static void main(String[] args) {
		Matrix m = new DefaultSparseRowMatrix2D<Double>(5, 5);
		System.out.println(m);
		m.setDouble(1.0, 1, 1);
		System.out.println(m.getDouble(1, 1));
		System.out.println(m);
	}

}
