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

package org.jdmp.core.util;

import java.util.ArrayList;
import java.util.List;

public class DefaultObservableList<V> extends AbstractObservableList<V> {
	private static final long serialVersionUID = -2636828198788691727L;

	private List<V> list = null;

	public DefaultObservableList() {
		this(new ArrayList<V>(2));
	}

	public DefaultObservableList(List<V> list) {
		this.list = list;
	}

	public List<V> getList() {
		return list;
	}

	public void setList(List<V> list) {
		this.list = list;
	}

	@Override
	public V get(int index) {
		return list.get(index);
	}

	@Override
	public void add(int index, V element) {
		list.add(index, element);
	}

	@Override
	public V remove(int index) {
		return list.remove(index);
	}

	@Override
	public V set(int index, V element) {
		return list.set(index, element);
	}

	@Override
	public int size() {
		return list.size();
	}

}
