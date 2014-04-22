/*
 * Copyright (C) 2008-2014 by Holger Arndt
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

package org.jdmp.core.util;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.interfaces.Wrapper;
import org.ujmp.core.objectmatrix.DenseObjectMatrix2D;
import org.ujmp.core.objectmatrix.factory.DenseObjectMatrix2DFactory;
import org.ujmp.core.objectmatrix.stub.AbstractDenseObjectMatrix2D;

public class MatrixListToMatrixWrapper extends AbstractDenseObjectMatrix2D implements
		Wrapper<ObservableList<Matrix>>, ListDataListener {
	private static final long serialVersionUID = 3023715319099124633L;

	private ObservableList<Matrix> matrixList = null;

	public MatrixListToMatrixWrapper(Variable variable) {
		super(0, 0);
		this.matrixList = variable.getMatrixList();
		matrixList.addListDataListener(this);
	}

	public long[] getSize() {
		long cols = 0;
		long rows = 0;
		for (Matrix m : matrixList) {
			cols = Math.max(m.getColumnCount(), cols);
			rows += m.getRowCount();
		}
		return new long[] { rows, cols };

	}

	public Object getObject(int row, int column) {
		return getObject((long) row, (long) column);
	}

	public Object getObject(long row, long column) {
		long rows = 0;
		for (Matrix m : matrixList) {
			rows += m.getRowCount();
			if (rows > row) {
				long r = row - rows + m.getRowCount();
				if (column < m.getColumnCount()) {
					return m.getAsObject(r, column);
				} else {
					return 0.0;
				}
			}
		}
		return 0.0;
	}

	public void setObject(Object value, int row, int column) {
		setObject(value, (long) row, (long) column);
	}

	public void setObject(Object value, long row, long column) {
		long rows = 0;
		for (Matrix m : matrixList) {
			rows += m.getRowCount();
			if (rows > row) {
				long r = row - rows + m.getRowCount();
				if (column < m.getColumnCount()) {
					m.setAsObject(value, r, column);
					return;
				} else {
					return;
				}
			}
		}
	}

	public ObservableList<Matrix> getWrappedObject() {
		return matrixList;
	}

	public void setWrappedObject(ObservableList<Matrix> object) {
		this.matrixList = object;
	}

	public void contentsChanged(ListDataEvent e) {
		notifyGUIObject();
	}

	public void intervalAdded(ListDataEvent e) {
		notifyGUIObject();
	}

	public void intervalRemoved(ListDataEvent e) {
		notifyGUIObject();
	}

	public DenseObjectMatrix2DFactory<? extends DenseObjectMatrix2D> getFactory() {
		// TODO Auto-generated method stub
		return null;
	}

}
