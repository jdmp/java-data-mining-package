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

package org.jdmp.gui.dataset;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import org.jdmp.core.dataset.DataSet;
import org.jdmp.gui.variable.VariableListTableCellRenderer;

public class DataSetTableCellRenderer implements TableCellRenderer {

	private final DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();

	private final VariableListTableCellRenderer variableListTableCellRenderer = new VariableListTableCellRenderer();

	private DataSet dataSet = null;

	private Object o = null;

	private JLabel c = null;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {

		c = null;

		if (value instanceof DataSet)
			dataSet = (DataSet) value;
		else
			dataSet = null;

		if (dataSet != null) {
			switch (column) {
			case DataSetListTableModel.LABELCOLUMN:
				o = dataSet.getLabel();
				break;
			case DataSetListTableModel.VARIABLECOLUMN:
				return variableListTableCellRenderer.getTableCellRendererComponent(table, dataSet
						.getVariables(), isSelected, hasFocus, row, column);
			case DataSetListTableModel.SAMPLECOUNTCOLUMN:
				o = "" + dataSet.getSamples().getSize();
				break;
			case DataSetListTableModel.DATASETCOUNTCOLUMN:
				o = "" + dataSet.getDataSets().getSize();
				break;
			default:
				o = null;
				break;
			}

			c = (JLabel) renderer.getTableCellRendererComponent(table, o, isSelected, hasFocus,
					row, column);

			switch (column) {
			case DataSetListTableModel.ICONCOLUMN:
				c.setIcon(UIManager.getIcon("DataSet.icon"));
				break;
			default:
				c.setIcon(null);
				break;
			}

			c.setHorizontalAlignment(JLabel.CENTER);

		}

		return c;
	}
}
