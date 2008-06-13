package org.jdmp.core.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.AbstractListModel;

public class ObservableMap<V> extends AbstractListModel implements CoreObjectList<V> {
	private static final long serialVersionUID = -1811632376295464484L;

	private Map<Object, V> map = null;

	public ObservableMap() {
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

}
