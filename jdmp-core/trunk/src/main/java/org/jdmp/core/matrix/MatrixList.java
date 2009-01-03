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
import java.util.List;
import java.util.ListIterator;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ListSelectionModel;

import org.jdmp.core.util.CoreObjectList;
import org.ujmp.core.Matrix;
import org.ujmp.core.collections.RingBufferList;
import org.ujmp.core.exceptions.MatrixException;

public class MatrixList extends AbstractListModel implements CoreObjectList<Matrix> {
	private static final long serialVersionUID = -908619462706136008L;

	private ListSelectionModel rowSelectionModel = null;

	private ListSelectionModel columnSelectionModel = null;

	private RingBufferList<Matrix> matrixList = new RingBufferList<Matrix>();

	public MatrixList() {
	}

	public MatrixList(Matrix m) {
		matrixList.add(m);
	}

	public MatrixList(int maxCount) {
		this();
		setMaxCount(maxCount);
	}

	public MatrixList(MatrixList list) {
		for (Matrix m : list) {
			matrixList.add(m);
		}
	}

	public MatrixList(Matrix... matrices) {
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

	public int indexOf(Matrix o) {
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
		boolean result = matrixList.add(matrix);
		fireIntervalAdded(this, matrixList.size() - 1, matrixList.size() - 1);
		return result;
	}

	public Iterator<Matrix> iterator() {
		return matrixList.iterator();
	}

	private double getDoubleValueAt(int row, int col) throws MatrixException {
		return matrixList.get(row).getAsDouble(col);
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

	public int getMaxSize() {
		return matrixList.maxSize();
	}

	public void setMaxCount(int maxCount) {
		matrixList = new RingBufferList<Matrix>(maxCount);
	}

	public ListSelectionModel getRowSelectionModel() {
		if (rowSelectionModel == null) {
			rowSelectionModel = new DefaultListSelectionModel();
		}
		return rowSelectionModel;
	}

	public ListSelectionModel getColumnSelectionModel() {
		if (columnSelectionModel == null) {
			columnSelectionModel = new DefaultListSelectionModel();
		}
		return columnSelectionModel;
	}

	public final synchronized Matrix getFirst() {
		if (isEmpty()) {
			return null;
		}
		return get(0);
	}

	public final synchronized Matrix getLast() {
		if (isEmpty()) {
			return null;
		}
		return get(size() - 1);
	}

	public final double[][] getTrace(int i) {
		try {
			int hsize = size();
			double[][] ret = new double[hsize][2];
			for (int a = 0; a < hsize; a++) {
				Matrix m = get(a);
				ret[a][0] = 0.0;
				ret[a][1] = m.getAsDouble(i % m.getRowCount(), i / m.getRowCount());
			}
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public final double getMaxTime() {
		Matrix m = getLast();
		return (m == null) ? 0.0 : 0.0;
	}

	public final double getMinTime() {
		Matrix m = getFirst();
		return (m == null) ? 0.0 : 0.0;
	}

	public final double getLength() {
		return getMaxTime() - getMinTime();
	}

	public final long getTraceCount() {
		Matrix m = getFirst();
		return (m == null) ? 0 : m.getColumnCount() * m.getRowCount();
	}

	public final void addAll(MatrixList matrices) {
		for (Matrix m : matrices) {
			add(m);
		}
	}

	@Override
	public Matrix getElementAt(int index) {
		return get(index);
	}

	@Override
	public Collection<Matrix> toCollection() {
		return null;
	}

	@Override
	public int getSize() {
		return matrixList.size();
	}

}
