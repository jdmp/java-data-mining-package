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

import java.awt.Color;
import java.awt.Component;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import org.jdmp.core.sample.Sample;
import org.jdmp.core.util.ObservableMap;
import org.jdmp.core.variable.Variable;
import org.ujmp.gui.renderer.MatrixHeatmapRenderer;

public class SampleTableCellRenderer implements TableCellRenderer {

	private static final Border DEFAULT_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);

	private final DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();

	private final MatrixHeatmapRenderer matrixRenderer = new MatrixHeatmapRenderer();

	private Map<Integer, String> columnMap = null;

	private Sample sample = null;

	private Object o = null;

	private JLabel c = null;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {

		columnMap = ((SampleListTableModel) table.getModel()).getColumnMap();

		c = null;

		if (value instanceof Sample) {
			sample = (Sample) value;

		} else {
			sample = null;
		}

		if (sample != null) {

			Object key = columnMap.get(column);
			ObservableMap<Variable> variables = sample.getVariableMap();
			Variable v = variables.get(key);
			if (v == null) {
				o = "";
			} else {
				return matrixRenderer.getTableCellRendererComponent(table, v.getLatestMatrix(),
						isSelected, hasFocus, row, column);
			}

			c = (JLabel) renderer.getTableCellRendererComponent(table, o, isSelected, hasFocus,
					row, column);

			c.setIcon(null);
			c.setHorizontalAlignment(JLabel.CENTER);

		} else {

			c = (JLabel) renderer.getTableCellRendererComponent(table, o, isSelected, hasFocus,
					row, column);

			if (hasFocus) {
				Border border = null;
				if (isSelected) {
					border = UIManager.getBorder("Table.focusSelectedCellHighlightBorder");
				}
				if (border == null) {
					border = UIManager.getBorder("Table.focusCellHighlightBorder");
				}
				c.setBorder(border);

				if (!isSelected && table.isCellEditable(row, column)) {
					Color col;
					col = UIManager.getColor("Table.focusCellForeground");
					if (col != null) {
						c.setForeground(col);
					}
					col = UIManager.getColor("Table.focusCellBackground");
					if (col != null) {
						c.setBackground(col);
					}
				}
			} else {
				c.setBorder(DEFAULT_NO_FOCUS_BORDER);
			}

		}

		return c;
	}
}
