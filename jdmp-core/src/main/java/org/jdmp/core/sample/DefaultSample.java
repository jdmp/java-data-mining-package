/*
 * Copyright (C) 2008-2014 by Holger Arndt
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

package org.jdmp.core.sample;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.ujmp.core.Matrix;

public class DefaultSample extends AbstractSample {
	private static final long serialVersionUID = -3649758882404748630L;

	private final Map<String, Matrix> map;

	public DefaultSample() {
		super();
		this.map = new HashMap<String, Matrix>();
	}

	public final DefaultSample clone() {
		DefaultSample s = new DefaultSample();
		for (String k : keySet()) {
			Matrix v = get(k);
			s.put(k, v.clone());
		}
		return s;
	}

	public Matrix get(Object key) {
		return map.get(key);
	}

	public Set<String> keySet() {
		return map.keySet();
	}

	@Override
	protected void clearMap() {
		map.clear();
	}

	@Override
	protected Matrix removeFromMap(Object key) {
		return map.remove(key);
	}

	@Override
	protected Matrix putIntoMap(String key, Matrix value) {
		return map.put(key, value);
	}

}
