/*
 * Copyright (C) 2008-2016 by Holger Arndt
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

package org.jdmp.core.dataset;

import java.util.List;

import org.jdmp.core.sample.Sample;
import org.ujmp.core.listmatrix.DefaultListMatrix;

public class DefaultListDataSet extends AbstractListDataSet {
	private static final long serialVersionUID = -2887879051530049677L;

	private final List<Sample> list = new DefaultListMatrix<Sample>();

	public DefaultListDataSet() {
		super();
	}

	public ListDataSet clone() {
		AbstractListDataSet ds = null;
		try {
			ds = this.getClass().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Sample s : this) {
			ds.add(s.clone());
		}
		return ds;
	}

	@Override
	public Sample get(int index) {
		return list.get(index);
	}

	@Override
	public boolean addToList(Sample t) {
		return list.add(t);
	}

	@Override
	public void addToList(int index, Sample element) {
		list.add(index, element);
	}

	@Override
	public Sample removeFromList(int index) {
		return list.remove(index);
	}

	@Override
	public boolean removeFromList(Object o) {
		return list.remove(o);
	}

	@Override
	public Sample setToList(int index, Sample element) {
		return list.set(index, element);
	}

	@Override
	public void clearList() {
		list.clear();
	}

	@Override
	public int size() {
		return list.size();
	}

}
