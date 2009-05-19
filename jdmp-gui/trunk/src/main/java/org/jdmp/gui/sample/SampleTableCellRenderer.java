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

import java.awt.Component;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import org.jdmp.core.algorithm.regression.Regressor;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.util.ObservableMap;
import org.jdmp.core.variable.Variable;
import org.ujmp.gui.renderer.MatrixRenderer;

public class SampleTableCellRenderer implements TableCellRenderer {

	public static final Object WEIGHT = Regressor.WEIGHT;

	private final DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();

	private final MatrixRenderer matrixRenderer = new MatrixRenderer();

	private Set<Object> keys = new HashSet<Object>();

	private Map<Integer, Object> columnMap = new HashMap<Integer, Object>();

	private Sample sample = null;

	private Object o = null;

	private JLabel c = null;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {

		c = null;

		if (value instanceof Sample) {
			sample = (Sample) value;
			Set<Object> ks = sample.getVariables().keySet();
			for (Object key : ks) {
				if (!keys.contains(key)) {
					keys.add(key);
					columnMap.put(columnMap.size(), key);
				}
			}

		} else {
			sample = null;
		}

		if (sample != null) {

			switch (column) {
			case SampleListTableModel.ICONCOLUMN:
				o = "";
				break;
			default:
				Object key = columnMap.get(column - 1);
				ObservableMap<Variable> variables = sample.getVariables();
				Variable v = variables.get(key);
				if (v == null) {
					o = "";
					break;
				} else {
					return matrixRenderer.getTableCellRendererComponent(table, v.getMatrix(),
							isSelected, hasFocus, row, column);
				}
			}

			c = (JLabel) renderer.getTableCellRendererComponent(table, o, isSelected, hasFocus,
					row, column);

			switch (column) {
			case SampleListTableModel.ICONCOLUMN:
				c.setIcon(UIManager.getIcon("Sample.icon"));
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
