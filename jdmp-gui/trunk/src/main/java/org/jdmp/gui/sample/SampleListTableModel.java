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

package org.jdmp.gui.sample;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.AbstractTableModel;

import org.jdmp.core.sample.HasSamples;
import org.jdmp.core.sample.Sample;

public class SampleListTableModel extends AbstractTableModel implements ListDataListener {
	private static final long serialVersionUID = -5468178300746964431L;

	public static final int ICONCOLUMN = 0;

	public static final int LABELCOLUMN = 1;

	private HasSamples iSamples = null;

	public SampleListTableModel(HasSamples iSamples) {
		this.iSamples = iSamples;
		iSamples.getSamples().addListDataListener(this);
	}

	public int getRowCount() {
		return iSamples.getSamples().getSize();
	}

	public int getColumnCount() {
		if (iSamples.getSamples().isEmpty()) {
			return 2;
		} else {
			return 2 + iSamples.getSamples().getElementAt(0).getVariables().getSize();
		}
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case ICONCOLUMN:
			return "";
		case LABELCOLUMN:
			return "Label";
		default:
			if (iSamples.getSamples().isEmpty()) {
				return "unknown";
			} else {
				return iSamples.getSamples().getElementAt(0).getVariables().getElementAt(
						columnIndex - 2).getLabel();
			}
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return Sample.class;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return iSamples.getSamples().getElementAt(rowIndex);
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
