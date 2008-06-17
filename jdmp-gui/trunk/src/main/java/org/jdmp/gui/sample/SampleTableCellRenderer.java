package org.jdmp.gui.sample;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import org.jdmp.core.algorithm.regression.Regressor;
import org.jdmp.core.sample.DefaultSample;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.variable.Variable;
import org.jdmp.gui.matrix.MatrixRenderer;

public class SampleTableCellRenderer implements TableCellRenderer {

	public static final Object WEIGHT = Regressor.WEIGHT;

	private DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();

	private MatrixRenderer matrixRenderer = new MatrixRenderer();

	private DefaultSample sample = null;

	private Object o = null;

	private JLabel c = null;

	private Variable v = null;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {

		c = null;

		if (value instanceof DefaultSample)
			sample = (DefaultSample) value;
		else
			sample = null;

		if (sample != null) {

			switch (column) {
			case SampleListTableModel.LABELCOLUMN:
				o = sample.getLabel();
				break;
			case SampleListTableModel.INPUTCOLUMN:
				return matrixRenderer.getTableCellRendererComponent(table, sample
						.getMatrix(Sample.INPUT), isSelected, hasFocus, row, column);
			case SampleListTableModel.PREDICTEDCOLUMN:
				return matrixRenderer.getTableCellRendererComponent(table, sample
						.getMatrix(Sample.PREDICTED), isSelected, hasFocus, row, column);
			case SampleListTableModel.WEIGHTCOLUMN:
				return matrixRenderer.getTableCellRendererComponent(table, sample
						.getMatrix(Sample.WEIGHT), isSelected, hasFocus, row, column);
			case SampleListTableModel.TARGETCOLUMN:
				return matrixRenderer.getTableCellRendererComponent(table, sample
						.getMatrix(Sample.TARGET), isSelected, hasFocus, row, column);
			case SampleListTableModel.RMSECOLUMN:
				return matrixRenderer.getTableCellRendererComponent(table, sample
						.getMatrix(Sample.RMSE), isSelected, hasFocus, row, column);
			case SampleListTableModel.DIFFERENCECOLUMN:
				return matrixRenderer.getTableCellRendererComponent(table, sample
						.getMatrix(Sample.DIFFERENCE), isSelected, hasFocus, row, column);
			default:
				o = "";
				break;
			}

			c = (JLabel) renderer.getTableCellRendererComponent(table, o, isSelected, hasFocus,
					row, column);

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
