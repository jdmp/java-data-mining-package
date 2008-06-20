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

package org.jdmp.matrix.stubs;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jdmp.matrix.interfaces.MapMatrix;

public abstract class AbstractMapMatrix extends AbstractDenseObjectMatrix2D implements
		MapMatrix<Object, Object> {

	public abstract Map<Object, Object> getMap();

	public final long[] getSize() {
		return new long[] { size(), 2 };
	}

	public final Object getObject(long row, long column) {
		Object mapKey = getKey((int) row);
		if (column == 0) {
			return mapKey;
		} else if (column == 1) {
			return (mapKey == null ? null : getMap().get(mapKey));
		} else {
			return null;
		}

	}

	public final void setObject(Object key, long row, long column) {
	}

	private final Object getKey(int index) {
		if (getMap() instanceof List) {
			return ((List) getMap()).get(index);
		}
		Iterator it = keySet().iterator();
		for (int i = 0; it.hasNext() && i < index; i++) {
			it.next();
		}
		return it.hasNext() ? it.next() : null;
	}

	public final boolean containsKey(Object key) {
		return getMap().containsKey(key);
	}

	public final boolean containsValue(Object value) {
		return getMap().containsValue(value);
	}

	public final Set<java.util.Map.Entry<Object, Object>> entrySet() {
		return getMap().entrySet();
	}

	public final Object get(Object key) {
		return getMap().get(key);
	}

	public final boolean isEmpty() {
		return getMap().isEmpty();
	}

	public final Set<Object> keySet() {
		return getMap().keySet();
	}

	public final Object put(Object key, Object value) {
		Object v = getMap().put(key, value);
		notifyGUIObject();
		return v;
	}

	public final void putAll(Map<? extends Object, ? extends Object> m) {
		getMap().putAll(m);
		notifyGUIObject();
	}

	public final Object remove(Object key) {
		Object v = getMap().remove(key);
		notifyGUIObject();
		return v;
	}

	public final int size() {
		return getMap().size();
	}

	public final Collection<Object> values() {
		return getMap().values();
	}

}
