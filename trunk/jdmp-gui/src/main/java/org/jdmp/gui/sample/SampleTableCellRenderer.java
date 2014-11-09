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

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import org.jdmp.core.sample.Sample;
import org.ujmp.core.Matrix;
import org.ujmp.gui.renderer.MatrixHeatmapRenderer;
import org.ujmp.gui.util.ColorUtil;

public class SampleTableCellRenderer implements TableCellRenderer {

	private static final Border borderSelected = BorderFactory.createLineBorder(Color.blue, 1);
	private static final Border borderNotSelected = BorderFactory.createLineBorder(Color.white, 1);

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
			String key = columnMap.get(column);
			Object mv = sample.get(key);
			if (mv == null) {
				o = "";
			} else if (mv instanceof Matrix) {
				return matrixRenderer.getTableCellRendererComponent(table, mv, isSelected,
						hasFocus, row, column);
			} else {
				o = String.valueOf(mv);
			}

			c = (JLabel) renderer.getTableCellRendererComponent(table, o, isSelected, hasFocus,
					row, column);

			c.setIcon(null);
			c.setHorizontalAlignment(JLabel.CENTER);

			if (mv == null) {
				c.setBackground(Color.BLACK);
			} else if (mv instanceof Matrix) {
				c.setBackground(Color.WHITE);
			} else {
				c.setBackground(ColorUtil.fromObject(mv));
			}

		}

		if (isSelected) {
			c.setBorder(borderSelected);
		} else {
			c.setBorder(borderNotSelected);
		}

		return c;
	}
}
