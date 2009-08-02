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

	private DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();

	private Module module = null;

	private Object o = null;

	private JLabel c = null;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

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
				o = module.getDataSetCount();
				break;
			case ModuleListTableModel.VARIABLECOUNTCOLUMN:
				o = module.getVariableCount();
				break;
			case ModuleListTableModel.MODULECOUNTCOLUMN:
				o = null;
				break;
			case ModuleListTableModel.ALGORITHMCOUNTCOLUMN:
				o = module.getAlgorithmCount();
				break;
			default:
				o = null;
				break;
			}

			c = (JLabel) renderer.getTableCellRendererComponent(table, o, isSelected, hasFocus, row, column);

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
