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
import org.jdmp.matrix.coordinates.CoordinateSetToLongWrapper;
import org.jdmp.matrix.coordinates.Coordinates;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.stubs.AbstractSparseObjectMatrix;

public class DefaultSparseObjectMatrix extends AbstractSparseObjectMatrix {
	private static final long serialVersionUID = -7139128532871448340L;

	private Map<Coordinates, Object> values = new HashMap<Coordinates, Object>();

	private long[] size = null;

	private int maximumNumberOfEntries = -1;

	private double defaultValue = 0.0;

	public DefaultSparseObjectMatrix(Matrix m) throws MatrixException {
		this.size = Coordinates.copyOf(m.getSize());
		for (long[] c : m.allCoordinates()) {
			setDouble(m.getDouble(c), c);
		}
	}

	public DefaultSparseObjectMatrix(Matrix m, int maximumNumberOfEntries) throws MatrixException {
		this.size = Coordinates.copyOf(m.getSize());
		this.maximumNumberOfEntries = maximumNumberOfEntries;
		for (long[] c : m.allCoordinates()) {
			setDouble(m.getDouble(c), c);
		}
	}

	public DefaultSparseObjectMatrix(long... size) {
		this.size = Coordinates.copyOf(size);
	}

	public DefaultSparseObjectMatrix(int maximumNumberOfEntries, long... size) {
		this.size = Coordinates.copyOf(size);
		this.maximumNumberOfEntries = maximumNumberOfEntries;
	}

	public long[] getSize() {
		return size;
	}
	
	public void setSize(long...size) {
      this.size = size;
    }

	public Object getObject(long... coordinates) {
		return values.get(new Coordinates(coordinates));
	}

	public long getValueCount() {
		return values.size();
	}

	public void setObject(Object value, long... coordinates) {
		while (maximumNumberOfEntries > 0 && values.size() > maximumNumberOfEntries) {
			values.remove(values.keySet().iterator().next());
		}
		if (Coordinates.isSmallerThan(coordinates, size)) {
			values.put(new Coordinates(coordinates), value);
		}
	}

	public Iterable<long[]> allCoordinates() {
		return new CoordinateIterator2D(getSize());
	}

	public Iterable<long[]> availableCoordinates() {
		return new CoordinateSetToLongWrapper(values.keySet());
	}

	public boolean contains(long... coordinates) {
		return values.containsKey(new Coordinates(coordinates));
	}

}
