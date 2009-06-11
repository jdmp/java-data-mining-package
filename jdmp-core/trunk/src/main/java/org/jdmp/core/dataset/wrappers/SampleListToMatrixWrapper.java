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

package org.jdmp.core.dataset.wrappers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.jdmp.core.sample.Sample;
import org.jdmp.core.util.CoreObjectList;
import org.ujmp.core.Matrix;
import org.ujmp.core.objectmatrix.AbstractDenseObjectMatrix2D;
import org.ujmp.core.util.StringUtil;

public class SampleListToMatrixWrapper extends AbstractDenseObjectMatrix2D implements
		ListDataListener {
	private static final long serialVersionUID = -6691992117924601112L;

	private CoreObjectList<Sample> samples = null;

	private int columnCount = 0;

	private Set<Object> keys = new HashSet<Object>();

	private Object[] keysToShow = null;

	private Map<Integer, Object> columnMap = new HashMap<Integer, Object>();

	public SampleListToMatrixWrapper(CoreObjectList<Sample> samples) {
		this(samples, new Object[0]);
	}

	public SampleListToMatrixWrapper(CoreObjectList<Sample> samples, Object... keysToShow) {
		this.samples = samples;
		this.keysToShow = keysToShow;
		samples.addListDataListener(this);
	}

	@Override
	public Object getObject(long row, long column) {
		return getObject((int) row, (int) column);
	}

	@Override
	public Object getObject(int row, int column) {
		createIndex();
		Sample s = samples.getElementAt(row);
		Object key = columnMap.get(column);
		if (key == null) {
			return null;
		} else {
			Matrix m = s.getMatrix(key);
			if (m == null) {
				return m;
			} else {
				if (m.isScalar()) {
					return m.getAsObject(0, 0);
				} else {
					return m;
				}
			}
		}
	}

	@Override
	public void setObject(Object value, long row, long column) {
	}

	@Override
	public void setObject(Object value, int row, int column) {
	}

	private synchronized void createIndex() {
		if (keys != null && !keys.isEmpty()) {
			return;
		}
		keys = new HashSet<Object>();
		if (keysToShow == null || keysToShow.length == 0) {
			for (Sample s : samples) {
				if (s != null) {
					keys.addAll(s.getVariables().keySet());
				}
			}
			int i = 0;
			for (Object k : keys) {
				columnMap.put(i, k);
				setColumnLabel(i, StringUtil.convert(k));
				i++;
			}
		} else {
			for (int i = 0; i < keysToShow.length; i++) {
				keys.add(keysToShow[i]);
				columnMap.put(i, keysToShow[i]);
				setColumnLabel(i, StringUtil.convert(keysToShow[i]));
			}
		}

		columnCount = keys.size();
	}

	private synchronized void deleteIndex() {
		if (getAnnotation() != null) {
			getAnnotation().clear();
		}
		columnCount = 0;
		columnMap.clear();
		keys.clear();
	}

	@Override
	public long[] getSize() {
		createIndex();
		return new long[] { samples.getSize(), columnCount };
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		deleteIndex();
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		deleteIndex();
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
	}

}
