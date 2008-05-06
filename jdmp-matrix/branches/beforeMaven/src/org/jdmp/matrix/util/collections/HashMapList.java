package org.jdmp.matrix.util.collections;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HashMapList<K, V> extends HashMap<K, V> {
	private static final long serialVersionUID = -3287449965373105826L;

	public HashMapList() {
		super();
	}

	public HashMapList(Map<? extends K, ? extends V> m) {
		super(m);
	}

	public synchronized V put(K key, V value) {
		return super.put(key, value);
	}

	public synchronized int indexOf(V value) {
		Iterator<V> it = values().iterator();
		for (int i = 0; it.hasNext(); i++) {
			if (it.next().equals(value)) {
				return i;
			}
		}
		return -1;
	}

	public synchronized V get(int index) {
		Iterator<V> it = values().iterator();
		for (int i = 0; it.hasNext() && i < index; i++) {
			it.next();
		}
		return it.hasNext() ? it.next() : null;
	}

	public synchronized V remove(Object key) {
		return super.remove(key);
	}

}
