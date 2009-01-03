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

package org.jdmp.jgroups;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListSelectionModel;
import javax.swing.ListSelectionModel;

import org.jdmp.core.util.AbstractEvent.EventType;
import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.VariableEvent;
import org.jgroups.ChannelFactory;
import org.jgroups.JChannelFactory;
import org.jgroups.View;
import org.jgroups.blocks.ReplicatedHashMap;
import org.jgroups.blocks.ReplicatedHashMap.Notification;
import org.ujmp.core.Matrix;
import org.ujmp.core.collections.AbstractMatrixList;
import org.ujmp.core.collections.DefaultMatrixList;
import org.ujmp.core.collections.MapToListWrapper;
import org.ujmp.core.collections.MatrixList;
import org.ujmp.core.exceptions.MatrixException;

public class ReplicatedMatrixList extends AbstractMatrixList {
	private static final Logger logger = Logger.getLogger(DefaultMatrixList.class.getName());

	private List<Matrix> matrixList = null;

	private ReplicatedHashMap<Integer, Matrix> map = null;

	private ChannelFactory factory = null;

	private final PropsUDP props = new PropsUDP();

	private final boolean persist = false;

	private Variable variable = null;

	private ListSelectionModel rowSelectionModel = null;

	private ListSelectionModel columnSelectionModel = null;

	private int maxCount = 100;

	public ReplicatedMatrixList(Variable v, String name) {
		try {
			this.variable = v;
			factory = new JChannelFactory();
			props.setBindAddress(InetAddress.getLocalHost().getHostAddress());
			map = new ReplicatedHashMap<Integer, Matrix>(name, factory, props.toString(), persist,
					10000);
			matrixList = new MapToListWrapper<Matrix>(map);
			map.addNotifier(new MatrixNotificationListener(this));
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not start matrix", e);
		}

	}

	public ReplicatedMatrixList(Variable v, String label, Matrix m) {
		this(v, label);
		add(m);
	}

	public ReplicatedMatrixList(Variable v, String label, int maxCount) {
		this(v, label);
		setMaxCount(maxCount);
	}

	public ReplicatedMatrixList(Variable v, String label, MatrixList list) {
		this(v, label);
		for (Matrix m : list) {
			add(m);
		}
	}

	public ReplicatedMatrixList(Variable v, String label, Matrix... matrices) {
		this(v, label);
		for (Matrix m : matrices)
			add(m);
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

	public Matrix get(int index) {
		if (index < matrixList.size()) {
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

	public boolean add(Matrix matrix) {
		// delete if already full
		while (size() >= getMaxSize()) {
			matrixList.remove(0);
		}

		// append
		return matrixList.add(matrix);
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

	public void notifyVariable() {
		variable.fireVariableEvent(new VariableEvent(variable, EventType.ALLUPDATED));
	}

	public int getMaxSize() {
		return maxCount;
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
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

}

class MatrixNotificationListener implements Notification {

	private ReplicatedMatrixList matrixList = null;

	public MatrixNotificationListener(ReplicatedMatrixList matrixList) {
		this.matrixList = matrixList;
	}

	public void contentsCleared() {
		matrixList.notifyVariable();
	}

	public void contentsSet(Map new_entries) {
		matrixList.notifyVariable();
	}

	public void entryRemoved(Serializable key) {
		matrixList.notifyVariable();
	}

	public void entrySet(Serializable key, Serializable value) {
		matrixList.notifyVariable();
	}

	public void viewChange(View view, Vector new_mbrs, Vector old_mbrs) {
		matrixList.notifyVariable();
	}
}
