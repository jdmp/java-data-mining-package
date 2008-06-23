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

import java.util.ArrayList;
import java.util.List;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.calculation.Calculation.Ret;
import org.jdmp.matrix.coordinates.CoordinateIterator2D;
import org.jdmp.matrix.coordinates.Coordinates;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.stubs.AbstractSparseGenericMatrix;
import org.jdmp.matrix.util.MathUtil;

public class DefaultSparseColumnMatrix2D<A> extends AbstractSparseGenericMatrix<A> {
	private static final long serialVersionUID = -1943118812754494387L;

	private long[] size = new long[] { 1, 1 };

	private List<Matrix> columns = new ArrayList<Matrix>();

	public DefaultSparseColumnMatrix2D(long... size) {
		setSize(size);
	}

	public DefaultSparseColumnMatrix2D(Matrix m) {
		setSize(m.getSize());
		for (long[] c : m.availableCoordinates()) {
			setObject(m.getObject(c), c);
		}
	}

	@Override
	public A getObject(long... coordinates) throws MatrixException {
		Matrix m = columns.get((int) coordinates[COLUMN]);
		return (A) m.getObject(coordinates[ROW], 0);
	}

	@Override
	public Iterable<long[]> allCoordinates() {
		return new CoordinateIterator2D(getSize());
	}

	// TODO: this is certainly not the optimal way to do it!
	public Iterable<long[]> availableCoordinates() {
		List<long[]> coordinates = new ArrayList<long[]>();
		for (int i = 0; i < size[COLUMN]; i++) {
			for (long[] c : columns.get(i).availableCoordinates()) {
				coordinates.add(Coordinates.plus(c, new long[] { 0, i }));
			}
		}
		return coordinates;
	}

	@Override
	public boolean contains(long... coordinates) {
		if (Coordinates.isSmallerThan(coordinates, size)) {
			return getObject(coordinates) != null;
		} else {
			return false;
		}
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
		Matrix m = columns.get((int) coordinates[COLUMN]);
		m.setObject(o, coordinates[ROW], 0);
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
		while (columns.size() < size[COLUMN]) {
			columns.add(new DefaultSparseGenericMatrix<A>(size[ROW], 1l));
		}

		if (this.size[ROW] != size[ROW]) {
			for (Matrix m : columns) {
				m.setSize(size[ROW], 1);
			}
		}
		this.size = size;
	}

	public Matrix getColumn(long column) {
		return columns.get((int) column);
	}

	@Override
	public Matrix max(Ret returnType, int dimension) throws MatrixException {
		if (returnType == Ret.NEW) {

			if (dimension == ROW) {
				Matrix ret = MatrixFactory.zeros(1, getColumnCount());
				for (long[] c : availableCoordinates()) {
					double v = getDouble(c);
					if (v > ret.getDouble(0, c[COLUMN])) {
						ret.setDouble(v, 0, c[COLUMN]);
					}
				}
				return ret;
			} else if (dimension == COLUMN) {
				Matrix ret = MatrixFactory.zeros(getRowCount(), 1);
				for (long[] c : availableCoordinates()) {
					double v = getDouble(c);
					if (v > ret.getDouble(c[ROW], 0)) {
						ret.setDouble(v, c[ROW], 0);
					}
				}
				return ret;
			}

		}
		throw new MatrixException("not supported");
	}

	public Matrix selectColumns(Ret returnType, long... columns) throws MatrixException {
		if (returnType == Ret.LINK && columns.length == 1) {
			return getColumn(columns[0]);
		}
		return super.selectColumns(returnType, columns);
	}

}
