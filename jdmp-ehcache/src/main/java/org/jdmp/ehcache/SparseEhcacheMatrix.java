/*
 * Copyright (C) 2008-2010 by Holger Arndt
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

package org.jdmp.ehcache;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.util.Map;

import org.ujmp.core.Matrix;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.objectmatrix.stub.AbstractMapToSparseMatrixWrapper;

public class SparseEhcacheMatrix extends AbstractMapToSparseMatrixWrapper {
	private static final long serialVersionUID = -7743149828558906127L;

	private transient Map<Coordinates, Object> values = null;

	public SparseEhcacheMatrix(Matrix m) throws MatrixException, IOException {
		super(m);
	}

	public SparseEhcacheMatrix(Matrix m, int maximumNumberOfEntries) throws MatrixException,
			IOException {
		super(m, maximumNumberOfEntries);
	}

	public SparseEhcacheMatrix(long... size) throws MatrixException, IOException {
		super(size);
	}

	
	public Map<Coordinates, Object> getMap() {
		if (values == null) {
			try {
				values = new EhcacheMap<Coordinates, Object>("matrix" + System.nanoTime());
			} catch (IOException e) {
				throw new MatrixException(e);
			}
		}
		return values;
	}

	
	public void setMap(Map<Coordinates, Object> map) {
		this.values = map;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();
		for (long[] c : availableCoordinates()) {
			s.writeObject(new Coordinates(c));
			s.writeObject(getObject(c));
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		while (true) {
			try {
				Coordinates c = (Coordinates) s.readObject();
				Object o = s.readObject();
				setObject(o, c.dimensions);
			} catch (OptionalDataException e) {
				return;
			}
		}
	}

}
