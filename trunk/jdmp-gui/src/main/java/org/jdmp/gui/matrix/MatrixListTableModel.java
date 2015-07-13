/*
 * Copyright (C) 2008-2015 by Holger Arndt
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

package org.jdmp.gui.matrix;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.AbstractTableModel;

import org.jdmp.core.variable.Variable;
import org.ujmp.gui.MatrixGUIObject;

public class MatrixListTableModel extends AbstractTableModel implements ListDataListener {
	private static final long serialVersionUID = 1820859033991171760L;

	private final Variable variable;

	public static final int INDEXCOLUMN = 0;
	public static final int MATRIXCOLUMN = 1;
	public static final int MATRIXPLOTCOLUMN = 2;
	public static final int SIZECOLUMN = 3;
	public static final int LABELCOLUMN = 4;

	public MatrixListTableModel(Variable v) {
		this.variable = v;
	}

	public int getRowCount() {
		return variable.size();
	}

	public int getColumnCount() {
		return 5;
	}

	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case INDEXCOLUMN:
			return "Index";
		case MATRIXCOLUMN:
			return "Matrix";
		case MATRIXPLOTCOLUMN:
			return "Plot";
		case SIZECOLUMN:
			return "Size";
		case LABELCOLUMN:
			return "Label";
		default:
			return "x";
		}
	}

	public Class<?> getColumnClass(int columnIndex) {
		return MatrixGUIObject.class;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return variable.get(rowIndex);
	}

	public void contentsChanged(ListDataEvent e) {
		fireTableRowsUpdated(e.getIndex0(), e.getIndex1());
	}

	public void intervalAdded(ListDataEvent e) {
		fireTableRowsInserted(e.getIndex0(), e.getIndex1());
	}

	public void intervalRemoved(ListDataEvent e) {
		fireTableRowsDeleted(e.getIndex0(), e.getIndex1());
	}

}
