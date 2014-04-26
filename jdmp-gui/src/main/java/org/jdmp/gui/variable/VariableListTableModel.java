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

package org.jdmp.gui.variable;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.AbstractTableModel;

import org.jdmp.core.variable.HasVariableMap;
import org.jdmp.core.variable.Variable;

public class VariableListTableModel extends AbstractTableModel implements ListDataListener {
	private static final long serialVersionUID = -1032855724069297926L;

	public static final int IDCOLUMN = 0;

	public static final int LABELCOLUMN = 1;

	public static final int SIZECOLUMN = 2;

	public static final int MATRIXCOLUMN = 3;

	public static final int PLOTCOLUMN = 4;

	public static final int MATRIXCOUNTCOLUMN = 5;

	private HasVariableMap iVariables = null;

	public VariableListTableModel(HasVariableMap iVariables) {
		this.iVariables = iVariables;
		iVariables.getVariableMap().addListDataListener(this);
	}

	public int getRowCount() {
		return iVariables.getVariableMap().getSize();
	}

	public int getColumnCount() {
		return 6;
	}

	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case IDCOLUMN:
			return "Id";
		case LABELCOLUMN:
			return "Label";
		case SIZECOLUMN:
			return "Size";
		case MATRIXCOLUMN:
			return "Matrix";
		case PLOTCOLUMN:
			return "Plot";
		case MATRIXCOUNTCOLUMN:
			return "MatrixCount";
		default:
			return ("unknown");
		}
	}

	public Class<?> getColumnClass(int columnIndex) {
		return Variable.class;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return iVariables.getVariableMap().getElementAt(rowIndex);
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
