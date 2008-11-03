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
import org.ujmp.core.collections.DefaultMatrixList;
import org.ujmp.core.collections.MatrixList;
import org.ujmp.core.interfaces.HasMatrix;
import org.ujmp.core.interfaces.HasMatrixList;
import org.ujmp.core.interfaces.Wrapper;

public class SingleMatrixWrapper implements Wrapper<Matrix>, HasMatrixList {

	private MatrixList matrixList = new DefaultMatrixList(1);

	private ListSelectionModel rowSelectionModel = null;

	private ListSelectionModel columnSelectionModel = null;

	public SingleMatrixWrapper(HasMatrix im) {
		matrixList.add(im.getMatrix());
	}

	public int getMatrixCount() {
		return 1;
	}

	public MatrixList getMatrixList() {
		return matrixList;
	}

	public void fireValueChanged(Matrix m) {
	}

	public ListSelectionModel getColumnSelectionModel() {
		if (columnSelectionModel == null) {
			columnSelectionModel = new DefaultListSelectionModel();
		}
		return columnSelectionModel;
	}

	public ListSelectionModel getRowSelectionModel() {
		if (rowSelectionModel == null) {
			rowSelectionModel = new DefaultListSelectionModel();
		}
		return rowSelectionModel;
	}

	public Matrix getMatrix(int index) {
		return matrixList.get(index);
	}

	public Matrix getWrappedObject() {
		return matrixList.get(0);
	}

	public void setWrappedObject(Matrix object) {
		matrixList.set(0, object);
	}
}