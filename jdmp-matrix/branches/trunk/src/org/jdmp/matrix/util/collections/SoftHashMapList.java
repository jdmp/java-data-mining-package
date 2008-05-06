package org.jdmp.matrix.util.collections;

import java.util.Iterator;
import java.util.Map;

public class SoftHashMapList<K, V> extends SoftHashMap<K, V> {

	public SoftHashMapList() {
		super();
	}

	public SoftHashMapList(Map<? extends K, ? extends V> m) {
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
