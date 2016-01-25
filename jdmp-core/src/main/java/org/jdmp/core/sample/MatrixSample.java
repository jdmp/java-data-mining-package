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

package org.jdmp.core.sample;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jdmp.core.dataset.DataSet;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;

public class MatrixSample extends AbstractSample {
	private static final long serialVersionUID = 8749986739854189724L;

	private final DataSet dataSet;

	private final int index;

	private final Map<String, Matrix> map = new HashMap<String, Matrix>();

	public MatrixSample(DataSet dataSet, int index) {
		this.dataSet = dataSet;
		this.index = index;
	}

	public synchronized Matrix get(Object key) {
		Matrix m = map.get(key);
		if (m == null) {
			Matrix all = dataSet.getMatrix(key);
			if (all == null) {
				return null;
			} else if (all.getRowCount() < dataSet.size()) {
				return null;
			} else {
				m = all.selectRows(Ret.LINK, index);
				map.put(String.valueOf(key), m);
			}
		}
		return m;
	}

	public Set<String> keySet() {
		return dataSet.keySet();
	}

	@Override
	public Sample clone() {
		throw new RuntimeException("not suppored");
	}

	@Override
	protected void clearMap() {
		throw new RuntimeException("not suppored");
	}

	@Override
	protected Matrix removeFromMap(Object key) {
		throw new RuntimeException("not suppored");
	}

	@Override
	protected Matrix putIntoMap(String key, Object value) {
		Matrix mv;
		if (value instanceof Matrix) {
			mv = (Matrix) value;
		} else {
			mv = Matrix.Factory.linkToValue(value);
		}
		Matrix m = dataSet.getMatrix(key);
		if (m == null || m.isEmpty()) {
			m = Matrix.Factory.zeros(dataSet.size(), mv.getColumnCount());
			dataSet.setMatrix(key, m);
		}
		m.setContent(Ret.ORIG, mv, index, 0);
		return null;
	}

	public int size() {
		return dataSet.size();
	}

}
