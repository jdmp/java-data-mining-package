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
		values.clear();
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
