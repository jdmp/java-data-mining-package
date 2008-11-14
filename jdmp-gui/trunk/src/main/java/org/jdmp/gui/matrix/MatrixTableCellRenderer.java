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

package org.jdmp.gui.matrix;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import org.ujmp.core.util.StringUtil;
import org.ujmp.gui.MatrixGUIObject;
import org.ujmp.gui.matrix.MatrixRenderer;
import org.ujmp.gui.plot.MatrixPlot;

public class MatrixTableCellRenderer implements TableCellRenderer {
	private static final long serialVersionUID = 4107015466785369684L;

	protected static Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);

	private final DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();

	private final MatrixRenderer matrixRenderer = new MatrixRenderer();

	private final MatrixPlot matrixPlot = new MatrixPlot();

	private MatrixGUIObject matrix = null;

	private Object o = null;

	private JLabel l = null;

	public MatrixTableCellRenderer() {
		matrixPlot.getPlotSettings().setShowXAxis(false);
		matrixPlot.getPlotSettings().setShowYAxis(false);
		matrixPlot.getPlotSettings().setShowXGrid(false);
		matrixPlot.getPlotSettings().setShowYGrid(false);
		matrixPlot.getPlotSettings().setTimeLimit(10);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {

		l = null;

		if (value instanceof MatrixGUIObject)
			matrix = (MatrixGUIObject) value;
		else
			matrix = null;

		if (matrix != null) {
			switch (column) {
			case MatrixListTableModel.INDEXCOLUMN:
				o = row;
				break;
			case MatrixListTableModel.MATRIXCOLUMN:
				return matrixRenderer.getTableCellRendererComponent(table, matrix, isSelected,
						hasFocus, row, column);
			case MatrixListTableModel.MATRIXPLOTCOLUMN:
				return matrixPlot.getTableCellRendererComponent(table, matrix, isSelected,
						hasFocus, row, column);
			case MatrixListTableModel.SIZECOLUMN:
				o = "" + matrix.getColumnCount() + "x" + matrix.getRowCount();
				break;
			case MatrixListTableModel.LABELCOLUMN:
				o = StringUtil.format(matrix.getLabel());
				break;
			default:
				o = null;
				break;
			}

			l = (JLabel) renderer.getTableCellRendererComponent(table, o, isSelected, hasFocus,
					row, column);
			l.setIcon(null);
			l.setHorizontalAlignment(JLabel.CENTER);
		}

		return l;
	}

}
