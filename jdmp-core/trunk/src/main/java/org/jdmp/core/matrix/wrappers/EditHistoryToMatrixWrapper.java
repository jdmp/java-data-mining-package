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

package org.jdmp.core.matrix.wrappers;

import javax.swing.DefaultListSelectionModel;
import javax.swing.ListSelectionModel;

import org.ujmp.core.Matrix;
import org.ujmp.core.collections.MatrixList;
import org.ujmp.core.interfaces.HasMatrixList;
import org.ujmp.core.interfaces.HasSourceMatrix;
import org.ujmp.core.interfaces.Wrapper;

public class EditHistoryToMatrixWrapper implements Wrapper<HasSourceMatrix>, HasMatrixList {

	private HasSourceMatrix matrix = null;

	private ListSelectionModel rowSelection = new DefaultListSelectionModel();

	private ListSelectionModel columnSelection = new DefaultListSelectionModel();

	public EditHistoryToMatrixWrapper(HasSourceMatrix matrix) {
		this.matrix = matrix;
	}

	public void fireValueChanged(Matrix m) {
	}

	public ListSelectionModel getColumnSelectionModel() {
		return columnSelection;
	}

	public int getMatrixCount() {
		return matrix.getSourceMatrices().size();
	}

	public MatrixList getMatrixList() {
		return matrix.getSourceMatrices();
	}

	public ListSelectionModel getRowSelectionModel() {
		return rowSelection;
	}

	public Matrix getMatrix(int index) {
		return matrix.getSourceMatrices().get(index);
	}

	public HasSourceMatrix getWrappedObject() {
		return matrix;
	}

	public void setWrappedObject(HasSourceMatrix object) {
		this.matrix = object;
	}

}
