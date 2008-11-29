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

	public static final int WEIGHTCOLUMN = 2;

	public static final int INPUTCOLUMN = 3;

	public static final int TARGETCOLUMN = 4;

	public static final int PREDICTEDCOLUMN = 5;

	public static final int DIFFERENCECOLUMN = 6;

	public static final int RMSECOLUMN = 7;

	private HasSamples iSamples = null;

	public SampleListTableModel(HasSamples iSamples) {
		this.iSamples = iSamples;
		iSamples.getSamples().addListDataListener(this);
	}

	public int getRowCount() {
		return iSamples.getSamples().getSize();
	}

	public int getColumnCount() {
		return 8;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case ICONCOLUMN:
			return "";
		case LABELCOLUMN:
			return "Label";
		case WEIGHTCOLUMN:
			return "Weight";
		case INPUTCOLUMN:
			return "Input";
		case TARGETCOLUMN:
			return "Target";
		case PREDICTEDCOLUMN:
			return "Predicted";
		case DIFFERENCECOLUMN:
			return "Difference";
		case RMSECOLUMN:
			return "RMSE";
		default:
			return "unknown";
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
