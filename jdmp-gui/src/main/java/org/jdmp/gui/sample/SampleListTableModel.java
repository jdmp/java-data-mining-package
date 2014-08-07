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

package org.jdmp.gui.sample;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.AbstractTableModel;

import org.jdmp.core.sample.HasSampleMap;
import org.jdmp.core.sample.Sample;

public class SampleListTableModel extends AbstractTableModel implements ListDataListener {
	private static final long serialVersionUID = -5468178300746964431L;

	private Set<String> keys = new HashSet<String>();

	private Map<Integer, String> columnMap = new HashMap<Integer, String>();

	private HasSampleMap iSamples = null;

	public SampleListTableModel(HasSampleMap iSamples) {
		this.iSamples = iSamples;
		iSamples.getSampleMap().addListDataListener(this);
	}

	public int getRowCount() {
		return iSamples.getSampleMap().getSize();
	}

	public int getColumnCount() {
		return Math.max(1, keys.size());
	}

	public String getColumnName(int columnIndex) {
		String s = columnMap.get(columnIndex);
		return s == null ? "unknown" : s;
	}

	public Class<?> getColumnClass(int columnIndex) {
		return Sample.class;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Sample s = iSamples.getSampleMap().getElementAt(rowIndex);
		if (s == null) {
			return null;
		}
		for (String key : s.keySet()) {
			if (keys.add(key)) {
				columnMap.put(columnMap.size(), key);
				fireTableStructureChanged();
			}
		}
		return s;
	}

	public void contentsChanged(ListDataEvent e) {
		if (e.getIndex0() < 0 || e.getIndex1() < 0) {
			fireTableDataChanged();
		} else {
			try {
				fireTableRowsUpdated(e.getIndex0(), e.getIndex1());
			} catch (Exception ex) {
				ex.printStackTrace();
				fireTableDataChanged();
			}
		}
	}

	public void intervalAdded(ListDataEvent e) {
		try {
			fireTableRowsInserted(e.getIndex0(), e.getIndex1());
		} catch (Exception ex) {
			ex.printStackTrace();
			fireTableDataChanged();
		}
	}

	public void intervalRemoved(ListDataEvent e) {
		try {
			fireTableRowsDeleted(e.getIndex0(), e.getIndex1());
		} catch (Exception ex) {
			ex.printStackTrace();
			fireTableDataChanged();
		}
	}

	public Set<String> getKeys() {
		return keys;
	}

	public Map<Integer, String> getColumnMap() {
		return columnMap;
	}

}
