/*
 * Copyright (C) 2008-2015 by Holger Arndt
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

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class DefaultSample extends AbstractSample {
	private static final long serialVersionUID = -3649758882404748630L;

	private final Map<String, Object> map;

	public DefaultSample() {
		super();
		this.map = new TreeMap<String, Object>();
	}

	public final AbstractSample clone() {
		AbstractSample s = new DefaultSample();
		for (String k : keySet()) {
			Object v = get(k);
			s.put(k, v);
		}
		return s;
	}

	public Object get(Object key) {
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
	protected Object removeFromMap(Object key) {
		return map.remove(key);
	}

	@Override
	protected Object putIntoMap(String key, Object value) {
		return map.put(key, value);
	}

	public int size() {
		return map.size();
	}

}
