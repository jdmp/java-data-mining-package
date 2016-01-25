/*
 * Copyright (C) 2008-2016 by Holger Arndt
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.event.EventListenerList;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.ujmp.core.collections.list.FastArrayList;
import org.ujmp.core.interfaces.CoreObject;
import org.ujmp.core.interfaces.HasId;

public abstract class AbstractObservableMap<V extends CoreObject> implements ObservableMap<V> {
	private static final long serialVersionUID = 1138872662801106054L;

	private EventListenerList listenerList = null;

	private final Map<String, V> map;

	private List<String> list = null;

	public AbstractObservableMap(Map<String, V> map) {
		this.map = map;
	}

	public AbstractObservableMap() {
		map = new TreeMap<String, V>();
	}

	public final Map<String, V> getMap() {
		return map;
	}

	public final List<String> getList() {
		if (list == null) {
			list = new FastArrayList<String>(getMap().size());
		}
		if (list.size() != getMap().size()) {
			list.clear();
			list.addAll(getMap().keySet());
		}
		return list;
	}

	public final synchronized V getElementAt(int index) {
		return get(getList().get(index));
	}

	public final synchronized V get(Object key) {
		return getMap().get(key);
	}

	public void addAll(Collection<? extends V> objects) {
		for (V v : objects) {
			add(v);
		}
	}

	public void add(V object) {
		if (object instanceof HasId) {
			put(((HasId) object).getId(), object);
		} else {
			throw new RuntimeException("object does not have an id");
		}
	}

	public final synchronized int indexOf(V value) {
		return getList().indexOf(value);
	}

	public final synchronized int getSize() {
		return getMap().size();
	}

	public final synchronized V put(String key, V value) {
		// if (value != null && value instanceof HasId) {
		// ((HasId) value).setId(key);
		// }
		// getList().clear();
		V v = getMap().put(key, value);
		// int index = indexOf(value);
		// if (v == null) {
		// fireIntervalAdded(this, index, index);
		// } else {
		// fireContentsChanged(this, index, index);
		// }
		return v;
	}

	public final synchronized V remove(Object key) {
		int index = indexOf(getMap().get(key));
		V v = getMap().remove(key);
		getList().clear();
		if (index >= 0) {
			fireIntervalRemoved(this, index, index);
		}
		return v;
	}

	public final void fireContentsChanged() {
		fireContentsChanged(this, -1, -1);
	}

	public synchronized Iterator<V> iterator() {
		return getMap().values().iterator();
	}

	public final boolean isEmpty() {
		return getMap().isEmpty();
	}

	public final void clear() {
		int size = getMap().size();
		getMap().clear();
		getList().clear();
		fireIntervalRemoved(this, 0, size - 1);
	}

	public final Collection<V> toCollection() {
		return getMap().values();
	}

	public final Set<String> keySet() {
		return getMap().keySet();
	}

	public final boolean containsKey(Object key) {
		return getMap().containsKey(key);
	}

	public final boolean containsValue(Object value) {
		return getMap().containsValue(value);
	}

	public final Set<java.util.Map.Entry<String, V>> entrySet() {
		return getMap().entrySet();
	}

	public final void putAll(Map<? extends String, ? extends V> m) {
		for (String k : m.keySet()) {
			V v = m.get(k);
			put(k, v);
		}
	}

	public final int size() {
		return getMap().size();
	}

	public final Collection<V> values() {
		return getMap().values();
	}

	public final String toString() {
		return getMap().toString();
	}

	public void addListDataListener(ListDataListener l) {
		if (listenerList == null) {
			listenerList = new EventListenerList();
		}
		listenerList.add(ListDataListener.class, l);
	}

	public void removeListDataListener(ListDataListener l) {
		if (listenerList == null) {
			return;
		}
		listenerList.remove(ListDataListener.class, l);
	}

	protected void fireContentsChanged(Object source, int index0, int index1) {
		if (listenerList == null) {
			return;
		}
		Object[] listeners = listenerList.getListenerList();
		ListDataEvent e = null;

		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ListDataListener.class) {
				if (e == null) {
					e = new ListDataEvent(source, ListDataEvent.CONTENTS_CHANGED, index0, index1);
				}
				((ListDataListener) listeners[i + 1]).contentsChanged(e);
			}
		}
	}

	protected void fireIntervalAdded(Object source, int index0, int index1) {
		if (listenerList == null) {
			return;
		}
		Object[] listeners = listenerList.getListenerList();
		ListDataEvent e = null;

		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ListDataListener.class) {
				if (e == null) {
					e = new ListDataEvent(source, ListDataEvent.INTERVAL_ADDED, index0, index1);
				}
				((ListDataListener) listeners[i + 1]).intervalAdded(e);
			}
		}
	}

	protected void fireIntervalRemoved(Object source, int index0, int index1) {
		if (listenerList == null) {
			return;
		}
		Object[] listeners = listenerList.getListenerList();
		ListDataEvent e = null;

		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ListDataListener.class) {
				if (e == null) {
					e = new ListDataEvent(source, ListDataEvent.INTERVAL_REMOVED, index0, index1);
				}
				((ListDataListener) listeners[i + 1]).intervalRemoved(e);
			}
		}
	}

}
