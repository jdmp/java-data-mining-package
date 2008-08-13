package org.jdmp.gui.matrix;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import org.ujmp.core.util.StringUtil;
import org.ujmp.gui.matrix.MatrixGUIObject;
import org.ujmp.gui.matrix.MatrixRenderer;
import org.ujmp.gui.matrix.plot.MatrixPlot;

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
