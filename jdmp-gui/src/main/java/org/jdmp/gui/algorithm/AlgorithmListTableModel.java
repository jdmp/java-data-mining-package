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

package org.jdmp.gui.algorithm;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.AbstractTableModel;

import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.algorithm.HasAlgorithms;

public class AlgorithmListTableModel extends AbstractTableModel implements ListDataListener {
	private static final long serialVersionUID = -3779798282436020436L;

	public static final int ICONCOLUMN = 0;

	public static final int LABELCOLUMN = 1;

	public static final int VARIABLECOLUMN = 2;

	public static final int ALGORITHMLISTCOLUMN = 3;

	private HasAlgorithms iAlgorithms = null;

	public AlgorithmListTableModel(HasAlgorithms iAlgorithms) {
		this.iAlgorithms = iAlgorithms;
		iAlgorithms.getAlgorithms().addListDataListener(this);
	}

	public int getRowCount() {
		return iAlgorithms.getAlgorithms().getSize();
	}

	public int getColumnCount() {
		return 4;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case ICONCOLUMN:
			return "";
		case VARIABLECOLUMN:
			return "Variables";
		case ALGORITHMLISTCOLUMN:
			return "Algorithms";
		case LABELCOLUMN:
			return "Label";
		default:
			return "x";
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return Algorithm.class;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return iAlgorithms.getAlgorithms().getElementAt(rowIndex);
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		fireTableRowsUpdated(e.getIndex0(), e.getIndex1());
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		fireTableRowsInserted(e.getIndex0(), e.getIndex1());
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		fireTableRowsDeleted(e.getIndex0(), e.getIndex1());
	}

}
