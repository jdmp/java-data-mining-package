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

package org.jdmp.core.sample;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.jdmp.core.variable.DefaultVariables;
import org.jdmp.core.variable.LazyVariables;
import org.jdmp.core.variable.SingletonVariable;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.interfaces.Wrapper;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.StringUtil;

public abstract class AbstractMapSample extends AbstractSample implements
		Wrapper<Map<String, Object>> {
	private static final long serialVersionUID = -8219872482909787192L;

	private VariableMap variableMap = new VariableMap();

	public AbstractMapSample() {
		// setVariables(new VariableMapWrapper());
		setVariables(new LazyVariables());
	}

	public final Sample clone() {
		return SampleFactory.clone(this);
	}

	class VariableMapWrapper extends DefaultVariables {
		private static final long serialVersionUID = 1252960592502010150L;

		public VariableMapWrapper() {
			setMap(variableMap);
		}

	}

	class VariableMap implements Map<String, Variable>, Serializable {
		private static final long serialVersionUID = -8553571915359063034L;

		public void clear() {
			getWrappedObject().clear();
		}

		public boolean containsKey(Object key) {
			return containsKey(key);
		}

		public boolean containsValue(Object value) {
			throw new RuntimeException("not implemented");
		}

		public Set<java.util.Map.Entry<String, Variable>> entrySet() {
			throw new RuntimeException("not implemented");
		}

		public Variable get(Object key) {
			Variable v = new SingletonVariable(MathUtil.getMatrix(getWrappedObject().get(key)));
			v.setLabel(StringUtil.convert(key));
			return v;
		}

		public boolean isEmpty() {
			return getWrappedObject().isEmpty();
		}

		public Set<String> keySet() {
			return getWrappedObject().keySet();
		}

		public Variable put(String key, Variable value) {
			getWrappedObject().put(key, value.getMatrix());
			return null;
		}

		public void putAll(Map<? extends String, ? extends Variable> m) {
			for (String key : m.keySet()) {
				put(key, m.get(key));
			}
		}

		public Variable remove(Object key) {
			getWrappedObject().remove(key);
			return null;
		}

		public int size() {
			return getWrappedObject().size();
		}

		public Collection<Variable> values() {
			return new CollectionWrapper();
		}

	}

	class CollectionWrapper implements Collection<Variable>, Serializable {
		private static final long serialVersionUID = 5598318867114474374L;

		public boolean add(Variable e) {
			throw new RuntimeException("not implemented");
		}

		public boolean addAll(Collection<? extends Variable> c) {
			throw new RuntimeException("not implemented");
		}

		public void clear() {
			throw new RuntimeException("not implemented");
		}

		public boolean contains(Object o) {
			throw new RuntimeException("not implemented");
		}

		public boolean containsAll(Collection<?> c) {
			throw new RuntimeException("not implemented");
		}

		public boolean isEmpty() {
			throw new RuntimeException("not implemented");
		}

		public Iterator<Variable> iterator() {
			return new Iterator<Variable>() {

				Iterator<String> it = getWrappedObject().keySet().iterator();

				public boolean hasNext() {
					return it.hasNext();
				}

				public Variable next() {
					Object key = it.next();
					return variableMap.get(key);
				}

				public void remove() {
					throw new RuntimeException("not implemented");
				}
			};
		}

		public boolean remove(Object o) {
			throw new RuntimeException("not implemented");
		}

		public boolean removeAll(Collection<?> c) {
			throw new RuntimeException("not implemented");
		}

		public boolean retainAll(Collection<?> c) {
			throw new RuntimeException("not implemented");
		}

		public int size() {
			throw new RuntimeException("not implemented");
		}

		public Object[] toArray() {
			throw new RuntimeException("not implemented");
		}

		public <T> T[] toArray(T[] a) {
			throw new RuntimeException("not implemented");
		}

	}

}
