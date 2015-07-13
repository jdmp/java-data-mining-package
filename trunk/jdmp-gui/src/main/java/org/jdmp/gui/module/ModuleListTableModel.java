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

package org.jdmp.gui.module;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.AbstractTableModel;

import org.jdmp.core.module.HasModuleMap;
import org.jdmp.core.module.Module;

public class ModuleListTableModel extends AbstractTableModel implements ListDataListener {
	private static final long serialVersionUID = -1372599464095754625L;

	public static final int IDCOLUMN = 0;

	public static final int LABELCOLUMN = 1;

	public static final int ALGORITHMCOUNTCOLUMN = 2;

	public static final int VARIABLECOUNTCOLUMN = 3;

	public static final int MODULECOUNTCOLUMN = 4;

	public static final int DATASETCOUNTCOLUMN = 5;

	private HasModuleMap iModules = null;

	public ModuleListTableModel(HasModuleMap iModules) {
		this.iModules = iModules;
		iModules.getModuleMap().addListDataListener(this);
	}

	public int getRowCount() {
		return iModules.getModuleMap().getSize();
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
		case VARIABLECOUNTCOLUMN:
			return "VariableCount";
		case MODULECOUNTCOLUMN:
			return "ModuleCount";
		case DATASETCOUNTCOLUMN:
			return "DataSetCount";
		case ALGORITHMCOUNTCOLUMN:
			return "AlgorithmCount";
		default:
			return "unknown";

		}
	}

	public Class<?> getColumnClass(int columnIndex) {
		return Module.class;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return iModules.getModuleMap().getElementAt(rowIndex);
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
