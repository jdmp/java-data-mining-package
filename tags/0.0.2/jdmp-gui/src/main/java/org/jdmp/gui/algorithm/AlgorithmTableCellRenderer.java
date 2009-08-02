package org.jdmp.gui.algorithm;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.gui.variable.VariableListTableCellRenderer;
import org.ujmp.core.util.StringUtil;

public class AlgorithmTableCellRenderer implements TableCellRenderer {
	private static final long serialVersionUID = 990917492575992066L;

	private DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();

	private VariableListTableCellRenderer variableListTableCellRenderer = new VariableListTableCellRenderer();

	private Algorithm algorithm = null;

	private Object o = null;

	private JLabel c = null;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

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
			case AlgorithmListTableModel.CALLSCOLUMN:
				o = algorithm.getCallsPerSecond();
				break;
			case AlgorithmListTableModel.COUNTCOLUMN:
				o = algorithm.getCount();
				break;
			case AlgorithmListTableModel.INTERVALCOLUMN:
				o = algorithm.getInterval();
				break;
			case AlgorithmListTableModel.RUNTIMECOLUMN:
				o = StringUtil.format(algorithm.getRuntime() / 1000.0 / 1000.0 / 1000.0);
				break;
			case AlgorithmListTableModel.CALCTIMECOLUMN:
				o = StringUtil.format(algorithm.getCalculateTime() / 1000.0);
				break;
			case AlgorithmListTableModel.STEPSTODOCOLUMN:
				o = algorithm.getStepsToDo();
				break;
			case AlgorithmListTableModel.VARIABLECOLUMN:
				return variableListTableCellRenderer.getTableCellRendererComponent(table, algorithm.getVariableList(),
						isSelected, hasFocus, row, column);
			case AlgorithmListTableModel.ALGORITHMLISTCOLUMN:
				o = algorithm.getAlgorithmList();
				break;
			default:
				o = null;
				break;
			}

			c = (JLabel) renderer.getTableCellRendererComponent(table, o, isSelected, hasFocus, row, column);

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
