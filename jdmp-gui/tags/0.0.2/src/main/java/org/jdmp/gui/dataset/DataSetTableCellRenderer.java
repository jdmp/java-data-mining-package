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

	private DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();

	private VariableListTableCellRenderer variableListTableCellRenderer = new VariableListTableCellRenderer();

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
						.getVariableList(), isSelected, hasFocus, row, column);
			case DataSetListTableModel.SAMPLECOUNTCOLUMN:
				o = "" + dataSet.getSampleList().getSize();
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
