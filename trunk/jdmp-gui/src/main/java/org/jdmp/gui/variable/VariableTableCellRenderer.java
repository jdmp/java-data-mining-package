/*
 * Copyright (C) 2008-2009 by Holger Arndt
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

package org.jdmp.gui.variable;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import org.jdmp.core.variable.Variable;
import org.ujmp.gui.plot.MatrixPlot;
import org.ujmp.gui.renderer.MatrixRenderer;

public class VariableTableCellRenderer implements TableCellRenderer {
	private static final long serialVersionUID = 990917492575992066L;

	private final DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();

	private final MatrixRenderer matrixRenderer = new MatrixRenderer();

	private final MatrixPlot matrixPlot = new MatrixPlot();

	private Variable variable = null;

	private Object o = null;

	public VariableTableCellRenderer() {
		matrixPlot.getPlotSettings().setShowXAxis(false);
		matrixPlot.getPlotSettings().setShowYAxis(false);
		matrixPlot.getPlotSettings().setShowXGrid(false);
		matrixPlot.getPlotSettings().setShowYGrid(false);
		matrixPlot.getPlotSettings().setTimeLimit(10);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {

		JLabel l = null;

		if (value instanceof Variable)
			variable = (Variable) value;
		else
			variable = null;

		if (variable != null) {
			switch (column) {
			case VariableListTableModel.IDCOLUMN:
				o = variable.getId();
				break;
			case VariableListTableModel.LABELCOLUMN:
				o = variable.getLabel();
				break;
			case VariableListTableModel.MATRIXCOUNTCOLUMN:
				o = variable.getMatrixCount();
				break;
			case VariableListTableModel.SIZECOLUMN:
				o = "" + variable.getRowCount() + "x" + variable.getColumnCount();
				break;
			case VariableListTableModel.PLOTCOLUMN:
				return matrixPlot.getTableCellRendererComponent(table, variable.getMatrix(),
						isSelected, hasFocus, row, column);
			case VariableListTableModel.MATRIXCOLUMN:
				return matrixRenderer.getTableCellRendererComponent(table, variable.getMatrix(),
						isSelected, hasFocus, row, column);
			default:
				o = null;
				break;
			}

			l = (JLabel) renderer.getTableCellRendererComponent(table, o, isSelected, hasFocus,
					row, column);

			l.setHorizontalAlignment(JLabel.CENTER);

		}

		return l;
	}
}
