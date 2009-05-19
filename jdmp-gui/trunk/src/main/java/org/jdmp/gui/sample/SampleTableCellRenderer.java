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

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import org.jdmp.core.algorithm.regression.Regressor;
import org.jdmp.core.sample.Sample;
import org.ujmp.gui.renderer.MatrixRenderer;

public class SampleTableCellRenderer implements TableCellRenderer {

	public static final Object WEIGHT = Regressor.WEIGHT;

	private final DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();

	private final MatrixRenderer matrixRenderer = new MatrixRenderer();

	private Sample sample = null;

	private Object o = null;

	private JLabel c = null;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {

		c = null;

		if (value instanceof Sample)
			sample = (Sample) value;
		else
			sample = null;

		if (sample != null) {

			switch (column) {
			case SampleListTableModel.ICONCOLUMN:
				o = "";
				break;
			default:
				return matrixRenderer.getTableCellRendererComponent(table, sample.getVariables()
						.getElementAt(column - 1).getMatrix(), isSelected, hasFocus, row, column);
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
