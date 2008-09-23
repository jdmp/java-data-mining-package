package org.jdmp.gui.algorithm;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.gui.variable.VariableListTableCellRenderer;

public class AlgorithmTableCellRenderer implements TableCellRenderer {
	private static final long serialVersionUID = 990917492575992066L;

	private final DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();

	private final VariableListTableCellRenderer variableListTableCellRenderer = new VariableListTableCellRenderer();

	private Algorithm algorithm = null;

	private Object o = null;

	private JLabel c = null;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {

		c = null;

		if (value instanceof Algorithm)
			algorithm = (Algorithm) value;
		else
			algorithm = null;

		if (algorithm != null) {
			switch (column) {
			case AlgorithmListTableModel.LABELCOLUMN:
				o = algorithm.getLabel();
				break;
			case AlgorithmListTableModel.VARIABLECOLUMN:
				return variableListTableCellRenderer.getTableCellRendererComponent(table, algorithm
						.getVariableList(), isSelected, hasFocus, row, column);
			case AlgorithmListTableModel.ALGORITHMLISTCOLUMN:
				o = algorithm.getAlgorithmList();
				break;
			default:
				o = null;
				break;
			}

			c = (JLabel) renderer.getTableCellRendererComponent(table, o, isSelected, hasFocus,
					row, column);

			switch (column) {
			case AlgorithmListTableModel.ICONCOLUMN:
				c.setIcon(UIManager.getIcon("Algorithm.icon"));
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
