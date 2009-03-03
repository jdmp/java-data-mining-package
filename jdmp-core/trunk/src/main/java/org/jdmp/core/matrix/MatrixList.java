/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
 *
 * This file is part of the Universal Java Matrix Package (UJMP).
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership and licensing.
 *
 * UJMP is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * UJMP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with UJMP; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.jdmp.core.matrix;

import java.util.Collection;
import java.util.Iterator;

import javax.swing.AbstractListModel;

import org.jdmp.core.util.ObservableList;
import org.ujmp.core.Matrix;
import org.ujmp.core.collections.RingBufferList;

public class MatrixList extends AbstractListModel implements ObservableList<Matrix> {
	private static final long serialVersionUID = -908619462706136008L;

	private RingBufferList<Matrix> matrixList = null;

	public MatrixList() {
		this(10);
	}

	public MatrixList(int maxCount) {
		matrixList = new RingBufferList<Matrix>(maxCount);
	}

	public void clear() {
		matrixList.clear();
	}

	public int indexOf(Matrix o) {
		return matrixList.indexOf(o);
	}

	public boolean isEmpty() {
		return matrixList.isEmpty();
	}

	public synchronized void add(Matrix matrix) {
		matrixList.add(matrix);
		fireIntervalAdded(this, matrixList.size() - 1, matrixList.size() - 1);
	}

	public Iterator<Matrix> iterator() {
		return matrixList.iterator();
	}

	@Override
	public Matrix getElementAt(int index) {
		if (index >= 0 && index < matrixList.size()) {
			return matrixList.get(index);
		} else {
			return null;
		}
	}

	@Override
	public Collection<Matrix> toCollection() {
		return matrixList;
	}

	@Override
	public int getSize() {
		return matrixList.size();
	}

	@Override
	public void addAll(Collection<Matrix> values) {
		for (Matrix v : values) {
			add(v);
		}
	}

	@Override
	public synchronized boolean remove(Matrix value) {
		int index = matrixList.indexOf(value);
		boolean b = matrixList.remove(value);
		if (index >= 0) {
			fireIntervalRemoved(this, index, index);
		}
		return b;
	}

}
