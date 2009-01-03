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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractListModel;

public class ObservableList<V> extends AbstractListModel implements CoreObjectList<V> {
	private static final long serialVersionUID = -2636828198788691727L;

	private List<V> values = null;

	public ObservableList() {
		values = new ArrayList<V>();
	}

	public synchronized V getElementAt(int index) {
		return values.get(index);
	}

	public synchronized void clear() {
		int size = values.size();
		values.clear();
		fireIntervalRemoved(this, 0, size - 1);
	}

	public synchronized int getSize() {
		return values.size();
	}

	public synchronized int indexOf(V value) {
		return values.indexOf(value);
	}

	public synchronized void add(V value) {
		values.add(value);
		fireIntervalAdded(this, values.size() - 1, values.size() - 1);
	}

	public void addAll(Collection<V> values) {
		for (V v : values) {
			add(v);
		}
	}

	public synchronized boolean remove(V value) {
		int index = values.indexOf(value);
		boolean b = values.remove(value);
		if (index >= 0) {
			fireIntervalRemoved(this, index, index);
		}
		return b;
	}

	public synchronized Iterator<V> iterator() {
		return values.iterator();
	}

	public boolean isEmpty() {
		return values.isEmpty();
	}

	public Collection<V> toCollection() {
		return values;
	}

}
