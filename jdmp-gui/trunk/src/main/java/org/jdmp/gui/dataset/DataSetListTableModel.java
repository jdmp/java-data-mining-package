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

package org.jdmp.gui.dataset;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.AbstractTableModel;

import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.dataset.HasDataSets;

public class DataSetListTableModel extends AbstractTableModel implements ListDataListener {
	private static final long serialVersionUID = 8897049622154020275L;

	public static final int ICONCOLUMN = 0;

	public static final int LABELCOLUMN = 1;

	public static final int SAMPLECOUNTCOLUMN = 2;

	public static final int VARIABLECOLUMN = 3;

	private HasDataSets iDataSets = null;

	public DataSetListTableModel(HasDataSets iDataSets) {
		this.iDataSets = iDataSets;
		iDataSets.getDataSets().addListDataListener(this);
	}

	public int getRowCount() {
		return iDataSets.getDataSets().getSize();
	}

	public int getColumnCount() {
		return 4;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case ICONCOLUMN:
			return "";
		case LABELCOLUMN:
			return "Label";
		case SAMPLECOUNTCOLUMN:
			return "SampleCount";
		case VARIABLECOLUMN:
			return "Variables";
		default:
			return "unknown";
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return DataSet.class;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return iDataSets.getDataSets().getElementAt(rowIndex);
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
