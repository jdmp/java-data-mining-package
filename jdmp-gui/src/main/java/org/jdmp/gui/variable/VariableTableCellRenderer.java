package org.jdmp.gui.variable;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import org.jdmp.core.variable.Variable;
import org.ujmp.gui.matrix.Matrix3DTableCellRenderer;
import org.ujmp.gui.matrix.MatrixRenderer;

public class VariableTableCellRenderer implements TableCellRenderer {
	private static final long serialVersionUID = 990917492575992066L;

	private final DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();

	private final MatrixRenderer matrixRenderer = new MatrixRenderer();

	private final Matrix3DTableCellRenderer timeSeriesRenderer = new Matrix3DTableCellRenderer();

	private Variable variable = null;

	private Object o = null;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {

		JLabel l = null;

		if (value instanceof Variable)
			variable = (Variable) value;
		else
			variable = null;

		if (variable != null) {
			switch (column) {
			case VariableListTableModel.LABELCOLUMN:
				o = variable.getLabel();
				break;
			case VariableListTableModel.MATRIXCOUNTCOLUMN:
				o = variable.getMatrixCount();
				break;
			case VariableListTableModel.SIZECOLUMN:
				o = "" + variable.getRowCount() + "x" + variable.getColumnCount();
				break;
			case VariableListTableModel.MEMORYSIZECOLUMN:
				o = variable.getMemorySize();
				break;
			case VariableListTableModel.HISTORYCOLUMN:
				return timeSeriesRenderer.getTableCellRendererComponent(table, variable
						.getMatrixList(), isSelected, hasFocus, row, column);
			case VariableListTableModel.MATRIXCOLUMN:
				return matrixRenderer.getTableCellRendererComponent(table, variable.getMatrix(),
						isSelected, hasFocus, row, column);
			default:
				o = null;
				break;
			}

			l = (JLabel) renderer.getTableCellRendererComponent(table, o, isSelected, hasFocus,
					row, column);

			switch (column) {
			case VariableListTableModel.ICONCOLUMN:
				l.setIcon(UIManager.getIcon("Variable.icon"));
				break;
			default:
				l.setIcon(null);
				break;
			}

			l.setHorizontalAlignment(JLabel.CENTER);

		}

		return l;
	}
}
