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

package org.jdmp.matrix.implementations.misc;

import java.util.Map;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.collections.SoftHashMap;
import org.jdmp.matrix.coordinates.CoordinateIterator2D;
import org.jdmp.matrix.coordinates.CoordinateSetToLongWrapper;
import org.jdmp.matrix.coordinates.Coordinates;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.stubs.AbstractSparseObjectMatrix;

public class VolatileSparseObjectMatrix extends AbstractSparseObjectMatrix {
	private static final long serialVersionUID = 392817709394048419L;

	private Map<Coordinates, Object> values = new SoftHashMap<Coordinates, Object>();

	private long[] size = null;

	public VolatileSparseObjectMatrix(Matrix m) throws MatrixException {
		this.size = Coordinates.copyOf(m.getSize());
		for (long[] c : m.allCoordinates()) {
			setDouble(m.getDouble(c), c);
		}
	}

	public boolean isTransient() {
		return true;
	}

	public VolatileSparseObjectMatrix(long... size) {
		this.size = Coordinates.copyOf(size);
	}

	public long[] getSize() {
		return size;
	}

	public Object getObject(long... coordinates) {
		return values.get(new Coordinates(coordinates));
	}

	public long getValueCount() {
		return values.size();
	}

	public void setObject(Object value, long... coordinates) {
		values.put(new Coordinates(coordinates), value);
	}

	public Iterable<long[]> allCoordinates() {
		return new CoordinateIterator2D(getSize());
	}

	public Iterable<long[]> entries() {
		return new CoordinateSetToLongWrapper(values.keySet());
	}

	public boolean contains(long... coordinates) {
		return values.containsKey(new Coordinates(coordinates));
	}

}
