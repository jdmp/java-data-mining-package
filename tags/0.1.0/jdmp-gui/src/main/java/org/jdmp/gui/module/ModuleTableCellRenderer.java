/*
 * Copyright (C) 2008-2009 Holger Aimport java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import org.jdmp.core.module.Module;
ree software; you can redistribute it and/or modify
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

package org.jdmp.gui.module;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import org.jdmp.core.module.Module;

public class ModuleTableCellRenderer implements TableCellRenderer {
	private static final long serialVersionUID = 990917492575992066L;

	private final DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();

	private Module module = null;

	private Object o = null;

	private JLabel c = null;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {

		c = null;

		if (value instanceof Module)
			module = (Module) value;
		else
			module = null;

		if (module != null) {

			switch (column) {
			case ModuleListTableModel.LABELCOLUMN:
				o = module.getLabel();
				break;
			case ModuleListTableModel.DATASETCOUNTCOLUMN:
				o = module.getDataSets().getSize();
				break;
			case ModuleListTableModel.VARIABLECOUNTCOLUMN:
				o = module.getVariables().getSize();
				break;
			case ModuleListTableModel.MODULECOUNTCOLUMN:
				o = null;
				break;
			case ModuleListTableModel.ALGORITHMCOUNTCOLUMN:
				o = module.getAlgorithms().getSize();
				break;
			default:
				o = null;
				break;
			}

			c = (JLabel) renderer.getTableCellRendererComponent(table, o, isSelected, hasFocus,
					row, column);

			switch (column) {
			case ModuleListTableModel.ICONCOLUMN:
				c.setIcon(UIManager.getIcon("Module.icon"));
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
