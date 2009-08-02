package org.jdmp.gui.sample;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import org.jdmp.core.sample.BasicSample;
import org.jdmp.core.sample.RegressionSample;
import org.jdmp.core.sample.WeightedSample;
import org.jdmp.core.variable.Variable;
import org.jdmp.gui.matrix.Matrix3DTableCellRenderer;
import org.jdmp.gui.matrix.MatrixRenderer;

public class SampleTableCellRenderer implements TableCellRenderer {

	private DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();

	private MatrixRenderer matrixRenderer = new MatrixRenderer();

	private Matrix3DTableCellRenderer timeSeriesRenderer = new Matrix3DTableCellRenderer();

	private BasicSample sample = null;

	private Object o = null;

	private JLabel c = null;

	private Variable v = null;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		c = null;

		if (value instanceof BasicSample)
			sample = (BasicSample) value;
		else
			sample = null;

		if (sample != null) {

			switch (column) {
			case SampleListTableModel.LABELCOLUMN:
				o = sample.getLabel();
				break;

			case SampleListTableModel.INPUTCOLUMN:
				v = sample.getInputVariable();
				if (v != null && v.getLength() > 0) {
					return timeSeriesRenderer.getTableCellRendererComponent(table, v.getMatrixList(), isSelected,
							hasFocus, row, column);
				} else if (v != null && v.getLength() == 0) {
					return matrixRenderer.getTableCellRendererComponent(table, v.getMatrix(), isSelected, hasFocus,
							row, column);
				} else {
					o = "n/a";
				}
				break;

			case SampleListTableModel.ACTUALOUTPUTCOLUMN:
				if (sample instanceof RegressionSample) {
					v = ((RegressionSample) sample).getOutputVariable();
					if (v != null && v.getLength() > 0) {
						return timeSeriesRenderer.getTableCellRendererComponent(table, v.getMatrixList(), isSelected,
								hasFocus, row, column);
					} else if (v != null && v.getLength() == 0) {
						return matrixRenderer.getTableCellRendererComponent(table, v.getMatrix(), isSelected, hasFocus,
								row, column);
					}
				} else {
					o = "n/a";
				}
				break;

			case SampleListTableModel.WEIGHTCOLUMN:
				if (sample instanceof WeightedSample) {
					v = ((WeightedSample) sample).getWeightVariable();
					if (v != null && v.getLength() > 0) {
						return timeSeriesRenderer.getTableCellRendererComponent(table, v.getMatrixList(), isSelected,
								hasFocus, row, column);
					} else if (v != null && v.getLength() == 0) {
						return matrixRenderer.getTableCellRendererComponent(table, v.getMatrix(), isSelected, hasFocus,
								row, column);
					}
				} else {
					o = "n/a";
				}
				break;
			case SampleListTableModel.DESIREDOUTPUTCOLUMN:
				if (sample instanceof RegressionSample) {
					v = ((RegressionSample) sample).getDesiredOutputVariable();
					if (v != null && v.getLength() > 0) {
						return timeSeriesRenderer.getTableCellRendererComponent(table, v.getMatrixList(), isSelected,
								hasFocus, row, column);
					} else if (v != null && v.getLength() == 0) {
						return matrixRenderer.getTableCellRendererComponent(table, v.getMatrix(), isSelected, hasFocus,
								row, column);
					}
				} else {
					o = "n/a";
				}
				break;
			case SampleListTableModel.RMSECOLUMN:
				if (sample instanceof RegressionSample) {
					v = ((RegressionSample) sample).getRMSEVariable();
					if (v != null && v.getLength() > 0) {
						return timeSeriesRenderer.getTableCellRendererComponent(table, v.getMatrixList(), isSelected,
								hasFocus, row, column);
					} else if (v != null && v.getLength() == 0) {
						return matrixRenderer.getTableCellRendererComponent(table, v.getMatrix(), isSelected, hasFocus,
								row, column);
					}
				} else {
					o = "n/a";
				}
				break;
			case SampleListTableModel.OUTPUTERRORCOLUMN:
				if (sample instanceof RegressionSample) {
					v = ((RegressionSample) sample).getOutputErrorVariable();
					if (v != null && v.getLength() > 0) {
						return timeSeriesRenderer.getTableCellRendererComponent(table, v.getMatrixList(), isSelected,
								hasFocus, row, column);
					} else if (v != null && v.getLength() == 0) {
						return matrixRenderer.getTableCellRendererComponent(table, v.getMatrix(), isSelected, hasFocus,
								row, column);
					}
				} else {
					o = "n/a";
				}
				break;
			default:
				o = "";
				break;
			}

			c = (JLabel) renderer.getTableCellRendererComponent(table, o, isSelected, hasFocus, row, column);

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
