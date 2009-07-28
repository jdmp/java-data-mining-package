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

package org.jdmp.core.sample;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.jdmp.core.util.AbstractObservableMap;
import org.jdmp.core.variable.SingletonVariable;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.interfaces.Wrapper;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.StringUtil;

public abstract class AbstractMapSample extends AbstractSample implements
		Wrapper<Map<Object, Object>> {
	private static final long serialVersionUID = -8219872482909787192L;

	private VariableMap variableMap = new VariableMap();

	public AbstractMapSample() {
		setVariables(new VariableMapWrapper());
	}

	@Override
	public final Sample clone() {
		return SampleFactory.clone(this);
	}

	public void setMatrix(Object variableKey, Matrix matrix) {
		getWrappedObject().put(variableKey, matrix);
	}

	class VariableMapWrapper extends AbstractObservableMap<Variable> {
		private static final long serialVersionUID = 1252960592502010150L;

		@Override
		public Map<Object, Variable> getMap() {
			return variableMap;
		}

		@Override
		public void setMap(Map<Object, Variable> map) {
		}
	}

	class VariableMap implements Map<Object, Variable> {

		@Override
		public void clear() {
			getWrappedObject().clear();
		}

		@Override
		public boolean containsKey(Object key) {
			return containsKey(key);
		}

		@Override
		public boolean containsValue(Object value) {
			throw new RuntimeException("not implemented");
		}

		@Override
		public Set<java.util.Map.Entry<Object, Variable>> entrySet() {
			throw new RuntimeException("not implemented");
		}

		@Override
		public Variable get(Object key) {
			Variable v = new SingletonVariable(MathUtil.getMatrix(getWrappedObject().get(key)));
			v.setLabel(StringUtil.convert(key));
			return v;
		}

		@Override
		public boolean isEmpty() {
			return getWrappedObject().isEmpty();
		}

		@Override
		public Set<Object> keySet() {
			return getWrappedObject().keySet();
		}

		@Override
		public Variable put(Object key, Variable value) {
			getWrappedObject().put(key, value.getMatrix());
			return null;
		}

		@Override
		public void putAll(Map<? extends Object, ? extends Variable> m) {
			for (Object key : m.keySet()) {
				put(key, m.get(key));
			}
		}

		@Override
		public Variable remove(Object key) {
			getWrappedObject().remove(key);
			return null;
		}

		@Override
		public int size() {
			return getWrappedObject().size();
		}

		@Override
		public Collection<Variable> values() {
			return new CollectionWrapper() {
			};
		}

	}

	class CollectionWrapper implements Collection<Variable> {

		@Override
		public boolean add(Variable e) {
			throw new RuntimeException("not implemented");
		}

		@Override
		public boolean addAll(Collection<? extends Variable> c) {
			throw new RuntimeException("not implemented");
		}

		@Override
		public void clear() {
			throw new RuntimeException("not implemented");
		}

		@Override
		public boolean contains(Object o) {
			throw new RuntimeException("not implemented");
		}

		@Override
		public boolean containsAll(Collection<?> c) {
			throw new RuntimeException("not implemented");
		}

		@Override
		public boolean isEmpty() {
			throw new RuntimeException("not implemented");
		}

		@Override
		public Iterator<Variable> iterator() {
			return new Iterator<Variable>() {

				Iterator<Object> it = getWrappedObject().keySet().iterator();

				@Override
				public boolean hasNext() {
					return it.hasNext();
				}

				@Override
				public Variable next() {
					Object key = it.next();
					return variableMap.get(key);
				}

				@Override
				public void remove() {
					throw new RuntimeException("not implemented");
				}
			};
		}

		@Override
		public boolean remove(Object o) {
			throw new RuntimeException("not implemented");
		}

		@Override
		public boolean removeAll(Collection<?> c) {
			throw new RuntimeException("not implemented");
		}

		@Override
		public boolean retainAll(Collection<?> c) {
			throw new RuntimeException("not implemented");
		}

		@Override
		public int size() {
			throw new RuntimeException("not implemented");
		}

		@Override
		public Object[] toArray() {
			throw new RuntimeException("not implemented");
		}

		@Override
		public <T> T[] toArray(T[] a) {
			throw new RuntimeException("not implemented");
		}

	}

}
