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

import java.util.Collection;

import javax.swing.ListSelectionModel;

import org.jdmp.matrix.Matrix;

public interface MatrixList extends Iterable<Matrix> {

	public int getMaxCount();

	public void setMaxCount(int maxCount);

	public boolean addAll(Collection<? extends Matrix> c);

	public boolean addAll(int index, Collection<? extends Matrix> c);

	public Matrix getFirst();

	public Matrix getLast();

	public Matrix get(int i);

	public boolean isEmpty();

	public int size();

	public double[][] getTrace(int i);

	public double getMaxTime();

	public double getMinTime();

	public double getLength();

	public long getTraceCount();

	public ListSelectionModel getRowSelectionModel();

	public ListSelectionModel getColumnSelectionModel();

	public boolean add(Matrix matrix);

	public void addAll(MatrixList matrices);

	public void clear();

	public Matrix set(int index, Matrix m);

	public int indexOf(Object o);

}
