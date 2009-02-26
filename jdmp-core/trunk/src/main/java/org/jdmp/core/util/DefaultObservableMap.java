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

package org.jdmp.core.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractListModel;

public class DefaultObservableMap<V> extends AbstractListModel implements ObservableMap<V> {
	private static final long serialVersionUID = -1811632376295464484L;

	private Map<Object, V> map = null;

	public DefaultObservableMap() {
		map = new HashMap<Object, V>(2);
	}

	public synchronized V getElementAt(int index) {
		Iterator<V> it = map.values().iterator();
		for (int i = 0; it.hasNext() && i < index; i++) {
			it.next();
		}
		return it.hasNext() ? it.next() : null;
	}

	public synchronized V get(Object key) {
		return map.get(key);
	}

	public synchronized int indexOf(V value) {
		Iterator<V> it = map.values().iterator();
		for (int i = 0; it.hasNext(); i++) {
			if (it.next().equals(value)) {
				return i;
			}
		}
		return -1;
	}

	public synchronized int getSize() {
		return map.size();
	}

	public synchronized V put(Object key, V value) {
		V v = map.put(key, value);
		int index = indexOf(value);
		if (v == null) {
			fireIntervalAdded(this, index, index);
		} else {
			fireContentsChanged(this, index, index);
		}
		return v;
	}

	public synchronized V remove(Object key) {
		int index = indexOf(map.get(key));
		V v = map.remove(key);
		if (index >= 0) {
			fireIntervalRemoved(this, index, index);
		}
		return v;
	}

	public synchronized Iterator<V> iterator() {
		return map.values().iterator();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public void clear() {
		int size = map.size();
		map.clear();
		fireIntervalRemoved(this, 0, size - 1);
	}

	public Collection<V> toCollection() {
		return map.values();
	}

	public Set<Object> keySet() {
		return map.keySet();
	}

	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@Override
	public Set<java.util.Map.Entry<Object, V>> entrySet() {
		return map.entrySet();
	}

	@Override
	public void putAll(Map<? extends Object, ? extends V> m) {
		for (Object k : m.keySet()) {
			V v = m.get(k);
			put(k, v);
		}
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public Collection<V> values() {
		return map.values();
	}

}
