package org.jdmp.matrix.stubs;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jdmp.matrix.interfaces.MapMatrix;

public abstract class AbstractMapMatrix<K, V> extends AbstractDenseObjectMatrix2D<Object> implements MapMatrix<K, V> {

	public abstract Map<K, V> getMap();

	public final long[] getSize() {
		return new long[] { size(), 2 };
	}

	public final Object getObject(long... coordinates) {
		Object mapKey = getKey((int) coordinates[ROW]);
		if (coordinates[COLUMN] == 0) {
			return mapKey;
		} else if (coordinates[COLUMN] == 1) {
			return (mapKey == null ? null : getMap().get(mapKey));
		} else {
			return null;
		}

	}

	public final void setObject(Object key, long... coordinates) {
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

	public final Object getAxisAnnotation(int axis, int positionOnAxis) {
		if (axis == COLUMN) {
			switch (positionOnAxis) {
			case 0:
				return "Key";
			case 1:
				return "Value";
			}
		}
		return null;
	}

	public final void setAxisAnnotation(int axis, int positionOnAxis, Object value) {
	}

	public final boolean containsKey(Object key) {
		return getMap().containsKey(key);
	}

	public final boolean containsValue(Object value) {
		return getMap().containsValue(value);
	}

	public final Set<java.util.Map.Entry<K, V>> entrySet() {
		return getMap().entrySet();
	}

	public final V get(Object key) {
		return getMap().get(key);
	}

	public final boolean isEmpty() {
		return getMap().isEmpty();
	}

	public final Set<K> keySet() {
		return getMap().keySet();
	}

	public final V put(K key, V value) {
		V v = getMap().put(key, value);
		notifyGUIObject();
		return v;
	}

	public final void putAll(Map<? extends K, ? extends V> m) {
		getMap().putAll(m);
		notifyGUIObject();
	}

	public final V remove(Object key) {
		V v = getMap().remove(key);
		notifyGUIObject();
		return v;
	}

	public final int size() {
		return getMap().size();
	}

	public final Collection<V> values() {
		return getMap().values();
	}

}
