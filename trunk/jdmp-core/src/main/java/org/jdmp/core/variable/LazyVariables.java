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

package org.jdmp.core.variable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import javax.swing.event.EventListenerList;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.jdmp.core.util.ObservableMap;
import org.ujmp.core.Matrix;
import org.ujmp.core.collections.map.LazyMap;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.StringUtil;

public class LazyVariables implements ObservableMap<Variable>, VariableMap {
	private static final long serialVersionUID = -6124194623868561891L;

	private EventListenerList listenerList = null;

	private LazyMap<String, Object> map = new LazyMap<String, Object>();

	public Matrix getMatrix(String variableKey) {
		return MathUtil.getMatrix(map.get(variableKey));
	}

	public void setMatrix(String variableKey, Matrix matrix) {
		map.put(variableKey, MathUtil.getObject(matrix));
	}

	public void setObject(String variableKey, Object value) {
		map.put(variableKey, value);
	}

	public void setCallable(String variableKey, Callable<Object> value) {
		map.put(variableKey, value);
	}

	public String getAllAsString(String variableKey) {
		return StringUtil.convert(map.get(variableKey));
	}

	public Object getObject(String variableKey) {
		return map.get(variableKey);
	}

	public void add(Variable object) {
		throw new RuntimeException("not implemented");
	}

	public void addAll(Collection<? extends Variable> objects) {
		throw new RuntimeException("not implemented");
	}

	public void clear() {
		map.clear();
	}

	public final void fireContentsChanged() {
		fireContentsChanged(this, -1, -1);
	}

	public final synchronized Variable getElementAt(int index) {
		Iterator<String> it = map.keySet().iterator();
		for (int i = 0; it.hasNext() && i < index; i++) {
			it.next();
		}
		return it.hasNext() ? get(it.next()) : null;
	}

	public int indexOf(Variable value) {
		throw new RuntimeException("not implemented");
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public Collection<Variable> toCollection() {
		throw new RuntimeException("not implemented");
	}

	public int getSize() {
		return map.size();
	}

	public Iterator<Variable> iterator() {
		return new Iterator<Variable>() {

			private Iterator<String> it = map.keySet().iterator();

			public boolean hasNext() {
				return it.hasNext();
			}

			public Variable next() {
				String key = it.next();
				return get(key);
			}

			public void remove() {
				throw new RuntimeException("not implemented");
			}
		};
	}

	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		throw new RuntimeException("not implemented");
	}

	public Set<java.util.Map.Entry<String, Variable>> entrySet() {
		throw new RuntimeException("not implemented");
	}

	public Variable get(Object key) {
		Variable v = new DefaultVariable();
		v.add(MathUtil.getMatrix(map.get(key)));
		v.setLabel(StringUtil.convert(key));
		v.setId(StringUtil.convert(key));
		return v;
	}

	public Set<String> keySet() {
		return map.keySet();
	}

	public Variable put(String key, Variable value) {
		if (value != null) {
			Matrix m = value.getLast();
			map.put(key, m);
		}
		return null;
	}

	public void putAll(Map<? extends String, ? extends Variable> m) {
		throw new RuntimeException("not implemented");
	}

	public Variable remove(Object key) {
		map.remove(key);
		return null;
	}

	public int size() {
		return map.size();
	}

	public Collection<Variable> values() {
		throw new RuntimeException("not implemented");
	}

	public final BigInteger getAsBigInteger(String variableKey) {
		return MathUtil.getBigInteger(getObject(variableKey));
	}

	public final String getAsString(String variableKey) {
		return StringUtil.convert(getObject(variableKey));
	}

	public final boolean getAsBoolean(String variableKey) {
		return MathUtil.getBoolean(getObject(variableKey));
	}

	public final byte getAsByte(String variableKey) {
		return MathUtil.getByte(getObject(variableKey));
	}

	public final char getAsChar(String variableKey) {
		return MathUtil.getChar(getObject(variableKey));
	}

	public final double getAsDouble(String variableKey) {
		return MathUtil.getDouble(getObject(variableKey));
	}

	public final float getAsFloat(String variableKey) {
		return MathUtil.getFloat(getObject(variableKey));
	}

	public final int getAsInt(String variableKey) {
		return MathUtil.getInt(getObject(variableKey));
	}

	public final Object getAsObject(String variableKey) {
		return getObject(variableKey);
	}

	public final long getAsLong(String variableKey) {
		return MathUtil.getLong(getObject(variableKey));
	}

	public final short getAsShort(String variableKey) {
		return MathUtil.getShort(getObject(variableKey));
	}

	public final Date getAsDate(String variableKey) {
		return MathUtil.getDate(getObject(variableKey));
	}

	public final BigDecimal getAsBigDecimal(String variableKey) {
		return MathUtil.getBigDecimal(getObject(variableKey));
	}

	public final String toString() {
		return map.toString();
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
