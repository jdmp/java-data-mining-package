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

import org.jdmp.matrix.Coordinates;
import org.jdmp.matrix.GenericMatrix;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.stubs.AbstractMapToTiledMatrix2DWrapper;

public class DefaultTiledObjectMatrix2D<A> extends AbstractMapToTiledMatrix2DWrapper<A> {
	private static final long serialVersionUID = 6745798685307431625L;

	private Map<Coordinates, GenericMatrix<A>> values = null;

	public DefaultTiledObjectMatrix2D(long... size) {
		super(size);
	}

	public DefaultTiledObjectMatrix2D(Matrix source) {
		super(source);
	}

	@Override
	public Map<Coordinates, GenericMatrix<A>> getMap() {
		if (values == null) {
			values = new HashMap<Coordinates, GenericMatrix<A>>();
		}
		return values;
	}

	@Override
	public void setMap(Map<Coordinates, GenericMatrix<A>> map) {
		this.values = map;
	}

}
