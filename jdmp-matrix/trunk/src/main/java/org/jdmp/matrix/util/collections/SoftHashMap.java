package org.jdmp.matrix.util.collections;

import java.lang.ref.SoftReference;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SoftHashMap<K, V> implements Map<K, V> {

	private static final Logger logger = Logger.getLogger(SoftHashMap.class.getName());

	private transient Map<K, SoftReference<V>> map = null;

	public SoftHashMap() {
		map = new HashMap<K, SoftReference<V>>();
	}

	public SoftHashMap(Map<? extends K, ? extends V> map) {
		this();
		putAll(map);
	}

	public void clear() {
		map.clear();
	}

	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@SuppressWarnings("unchecked")
	public boolean containsValue(Object value) {
		return map.containsValue(new SoftReference((V) value));
	}

	public Set<java.util.Map.Entry<K, V>> entrySet() {
		logger.log(Level.WARNING, "cannot get entryset");
		return null;
	}

	public V get(Object key) {
		SoftReference<V> v = map.get(key);
		return v == null ? null : v.get();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public Set<K> keySet() {
		return map.keySet();
	}

	public V put(K key, V value) {
		SoftReference<V> v = map.put(key, new SoftReference<V>(value));
		return v == null ? null : v.get();
	}

	public void putAll(Map<? extends K, ? extends V> m) {
		for (K key : m.keySet()) {
			put(key, m.get(key));
		}
	}

	public V remove(Object key) {
		SoftReference<V> v = map.remove(key);
		return v == null ? null : v.get();
	}

	public int size() {
		return map.size();
	}

	public Collection<V> values() {
		logger.log(Level.WARNING, "cannot get values");
		return null;
	}

}
