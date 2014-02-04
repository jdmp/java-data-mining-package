/*
 * Copyright (C) 2008-2014 by Holger Arndt
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

package org.jdmp.jetty.collections;

import java.net.URL;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.jdmp.jetty.JettyObjectClient;

public class JettyMapClient<K, V> implements Map<K, V> {

	private JettyObjectClient client = null;

	public JettyMapClient(URL url) {
		client = new JettyObjectClient(url);
	}

	public void clear() {
		try {
			client.execute("clear");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public boolean containsKey(Object key) {
		try {
			return (Boolean) client.execute("containsKey", key);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public boolean containsValue(Object value) {
		try {
			return (Boolean) client.execute("containsValue", value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		try {
			return (Set<java.util.Map.Entry<K, V>>) client.execute("entrySet");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public V get(Object key) {
		try {
			return (V) client.execute("get", key);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public boolean isEmpty() {
		try {
			return (Boolean) client.execute("isEmpty");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public Set<K> keySet() {
		try {
			return (Set<K>) client.execute("keySet");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public V put(K key, V value) {
		try {
			return (V) client.execute("put", key, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void putAll(Map<? extends K, ? extends V> m) {
		try {
			client.execute("putAll", m);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public V remove(Object key) {
		try {
			return (V) client.execute("remove", key);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public int size() {
		try {
			return (Integer) client.execute("size");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public Collection<V> values() {
		try {
			return (Collection<V>) client.execute("values");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
