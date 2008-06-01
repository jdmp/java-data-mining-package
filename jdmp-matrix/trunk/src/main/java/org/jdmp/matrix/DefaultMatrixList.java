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

package org.jdmp.matrix;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.jdmp.matrix.util.collections.RingBufferList;

public class DefaultMatrixList extends MatrixList {
	private static final long serialVersionUID = -908619462706136008L;

	private RingBufferList<Matrix> matrixList = new RingBufferList<Matrix>();

	public DefaultMatrixList() {
	}

	public DefaultMatrixList(Matrix m) {
		matrixList.add(m);
	}

	public DefaultMatrixList(int maxCount) {
		this();
		setMaxCount(maxCount);
	}

	public DefaultMatrixList(MatrixList list) {
		for (Matrix m : list) {
			matrixList.add(m);
		}
	}

	public DefaultMatrixList(Matrix... matrices) {
		for (Matrix m : matrices)
			matrixList.add(m);
	}

	public void add(int index, Matrix element) {
		matrixList.add(index, element);
	}

	public boolean addAll(Collection<? extends Matrix> c) {
		return matrixList.addAll(c);
	}

	public boolean addAll(int index, Collection<? extends Matrix> c) {
		return matrixList.addAll(index, c);
	}

	public void clear() {
		matrixList.clear();
	}

	public boolean contains(Object o) {
		return matrixList.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return matrixList.containsAll(c);
	}

	public synchronized Matrix get(int index) {
		if (index >= 0 && index < matrixList.size()) {
			return matrixList.get(index);
		} else {
			return null;
		}
	}

	public int indexOf(Object o) {
		return matrixList.indexOf(o);
	}

	public boolean isEmpty() {
		return matrixList.isEmpty();
	}

	public int lastIndexOf(Object o) {
		return matrixList.lastIndexOf(o);
	}

	public ListIterator<Matrix> listIterator() {
		return matrixList.listIterator();
	}

	public ListIterator<Matrix> listIterator(int index) {
		return matrixList.listIterator(index);
	}

	public boolean remove(Object o) {
		return matrixList.remove(o);
	}

	public Matrix remove(int index) {
		return matrixList.remove(index);
	}

	public boolean removeAll(Collection<?> c) {
		return matrixList.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return matrixList.retainAll(c);
	}

	public Matrix set(int index, Matrix element) {
		return matrixList.set(index, element);
	}

	public int size() {
		return matrixList.size();
	}

	public List<Matrix> subList(int fromIndex, int toIndex) {
		return matrixList.subList(fromIndex, toIndex);
	}

	public <T> T[] toArray(T[] a) {
		return matrixList.toArray(a);
	}

	public void append(Matrix e) {
		add(e);
	}

	public final int getMatrixCount() {
		return matrixList.size();
	}

	public synchronized boolean add(Matrix matrix) {
		// delete if already full
		// while (size() >= getMaxCount()) {
		// matrixList.remove(0);
		// }

		// append
		return matrixList.add(matrix);
	}

	public Iterator<Matrix> iterator() {
		return matrixList.iterator();
	}

	private double getDoubleValueAt(int row, int col) throws MatrixException {
		return matrixList.get(row).getDouble(col);
	}

	public final double[][] toArray() throws MatrixException {
		double[][] values = new double[matrixList.size()][(int) getLast().getValueCount()];
		for (int i = 0; i < matrixList.size(); i++) {
			for (int j = 0; j < (int) getLast().getValueCount(); j++) {
				values[i][j] = getDoubleValueAt(i, j);
			}
		}
		return values;
	}

	@Override
	public int getMaxCount() {
		return matrixList.maxSize();
	}

	@Override
	public void setMaxCount(int maxCount) {
		matrixList = new RingBufferList<Matrix>(maxCount);
	}

}
