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

package org.jdmp.core.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.event.EventListenerList;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public abstract class AbstractObservableList<V> implements ObservableList<V> {
	private static final long serialVersionUID = -6099014976896834236L;

	private EventListenerList listenerList = null;

	public abstract List<V> getList();

	public abstract void setList(List<V> list);

	public final synchronized V getElementAt(int index) {
		if (index >= 0 && index < getList().size()) {
			return getList().get(index);
		} else {
			return null;
		}
	}

	public final synchronized void clear() {
		int size = getList().size();
		getList().clear();
		fireIntervalRemoved(this, 0, size - 1);
	}

	public final synchronized int getSize() {
		return getList().size();
	}

	public final synchronized int indexOf(V value) {
		return getList().indexOf(value);
	}

	public final synchronized void add(V value) {
		getList().add(value);
		fireIntervalAdded(this, getList().size() - 1, getList().size() - 1);
	}

	public final void addAll(Collection<V> values) {
		for (V v : values) {
			add(v);
		}
	}

	public final synchronized boolean remove(V value) {
		int index = getList().indexOf(value);
		boolean b = getList().remove(value);
		if (index >= 0) {
			fireIntervalRemoved(this, index, index);
		}
		return b;
	}

	public final synchronized Iterator<V> iterator() {
		return getList().iterator();
	}

	public final boolean isEmpty() {
		return getList().isEmpty();
	}

	public final Collection<V> toCollection() {
		return getList();
	}

	public final String toString() {
		return getList().toString();
	}

	
	public final void fireContentsChanged() {
		fireContentsChanged(this, -1, -1);
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
