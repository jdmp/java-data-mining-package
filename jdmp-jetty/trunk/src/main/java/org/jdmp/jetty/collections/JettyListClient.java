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

package org.jdmp.jetty.collections;

import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.jdmp.jetty.JettyObjectClient;
import org.ujmp.core.exceptions.MatrixException;

public class JettyListClient<V> implements List<V> {

	private JettyObjectClient client = null;

	public JettyListClient(URL url) {
		client = new JettyObjectClient(url);
	}

	@Override
	public boolean add(V e) {
		try {
			return (Boolean) client.execute("add", e);
		} catch (Exception ex) {
			throw new MatrixException(ex);
		}
	}

	@Override
	public void add(int index, V element) {
		try {
			client.execute("add", index, element);
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	@Override
	public boolean addAll(Collection<? extends V> c) {
		try {
			return (Boolean) client.execute("addAll", c);
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	@Override
	public boolean addAll(int index, Collection<? extends V> c) {
		try {
			return (Boolean) client.execute("addAll", index, c);
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	@Override
	public boolean contains(Object o) {
		try {
			return (Boolean) client.execute("contains", o);
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		try {
			return (Boolean) client.execute("containsAll", c);
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public V get(int index) {
		try {
			return (V) client.execute("get", index);
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	@Override
	public int indexOf(Object o) {
		try {
			return (Integer) client.execute("indexOf", o);
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	@Override
	public Iterator<V> iterator() {
		try {
			return (Iterator<V>) client.execute("iterator");
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	@Override
	public int lastIndexOf(Object o) {
		try {
			return (Integer) client.execute("lastIndexOf", o);
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ListIterator<V> listIterator() {
		try {
			return (ListIterator<V>) client.execute("listIterator");
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ListIterator<V> listIterator(int index) {
		try {
			return (ListIterator<V>) client.execute("listIterator", index);
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public V remove(int index) {
		try {
			return (V) client.execute("remove", index);
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		try {
			return (Boolean) client.execute("removeAll", c);
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		try {
			return (Boolean) client.execute("retainAll", c);
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public V set(int index, V element) {
		try {
			return (V) client.execute("set", index, element);
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<V> subList(int fromIndex, int toIndex) {
		try {
			return (List<V>) client.execute("subList", fromIndex, toIndex);
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	@Override
	public Object[] toArray() {
		try {
			return (Object[]) client.execute("toArray");
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(T[] a) {
		try {
			return (T[]) client.execute("toArray", a);
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	@Override
	public void clear() {
		try {
			client.execute("clear");
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	@Override
	public boolean isEmpty() {
		try {
			return (Boolean) client.execute("isEmpty");
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	@Override
	public int size() {
		try {
			return (Integer) client.execute("size");
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	@Override
	public boolean remove(Object o) {
		try {
			return (Boolean) client.execute("remove", o);
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

}
