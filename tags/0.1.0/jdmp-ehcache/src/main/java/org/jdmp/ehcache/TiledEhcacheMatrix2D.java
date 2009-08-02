/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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
import org.ujmp.core.objectmatrix.AbstractMapToTiledMatrix2DWrapper;
import org.ujmp.core.objectmatrix.ObjectMatrix2D;

public class TiledEhcacheMatrix2D extends AbstractMapToTiledMatrix2DWrapper {
	private static final long serialVersionUID = 4324063544046176423L;

	private transient Map<Coordinates, ObjectMatrix2D> values = null;

	public TiledEhcacheMatrix2D(long... size) {
		super(size);
	}

	public TiledEhcacheMatrix2D(Matrix source) {
		super(source);
	}

	@Override
	public Map<Coordinates, ObjectMatrix2D> getMap() {
		if (values == null) {
			try {
				values = new EhcacheMap<Coordinates, ObjectMatrix2D>();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return values;
	}

	@Override
	public void setMap(Map<Coordinates, ObjectMatrix2D> map) {
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
