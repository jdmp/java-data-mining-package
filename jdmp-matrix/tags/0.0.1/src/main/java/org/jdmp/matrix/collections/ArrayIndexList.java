/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
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

package org.jdmp.matrix.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * ArrayIndexList is like an ArrayList, but keeps track of the indices where
 * objects have been added. This improves the speed of indexOf() and contains()
 * 
 * @author Holger Arndt
 * 
 * @param <M>
 *            Type of the elements in the list
 */
public class ArrayIndexList<M> extends ArrayList<M> {
	private static final long serialVersionUID = 3657191905843442834L;

	private Map<M, Integer> indexMap = new HashMap<M, Integer>();

	@Override
	public void add(int index, M element) {
		new Exception("not implemented").printStackTrace();
	}

	@Override
	public boolean add(M e) {
		indexMap.put(e, size());
		return super.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends M> c) {
		for (M m : c) {
			add(m);
		}
		return true;
	}

	@Override
	public boolean addAll(int index, Collection<? extends M> c) {
		new Exception("not implemented").printStackTrace();
		return false;
	}

	@Override
	public void clear() {
		indexMap.clear();
		super.clear();
	}

	@Override
	public boolean contains(Object o) {
		return indexMap.containsKey(o);
	}

	@Override
	public int indexOf(Object o) {
		return indexMap.get(o);
	}

	@Override
	public int lastIndexOf(Object o) {

		return super.lastIndexOf(o);
	}

	@Override
	public M remove(int index) {
		M m = super.remove(index);
		indexMap.remove(m);
		return m;
	}

	@Override
	public boolean remove(Object o) {
		indexMap.remove(o);
		return super.remove(o);
	}

	@Override
	public M set(int index, M element) {
		new Exception("not implemented").printStackTrace();
		return null;
	}

}
