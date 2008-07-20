package org.jdmp.gui.matrix;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

import org.jdmp.gui.util.ColorUtil;
import org.ujmp.core.util.StringUtil;

public class MatrixValueTableCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = -1473046176750819621L;

	private static final Color SELECTCOLOR = new Color(200, 200, 255);

	private Border border = BorderFactory.createLineBorder(Color.blue, 2);

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {
		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected,
				hasFocus, row, column);
		label.setHorizontalAlignment(JLabel.CENTER);

		MatrixGUIObject m = (MatrixGUIObject) table.getModel();

		Color c = ColorUtil.fromObject(value);

		int width = table.getColumnModel().getColumn(column).getWidth();
		if (width < 25) {
			label.setText("");
		} else {
			label.setText(StringUtil.format(value));
		}
		label.setForeground(ColorUtil.contrastBW(c));
		label.setBackground(c);
		if (isSelected) {
			label.setBorder(border);
			// label.setBackground(SELECTCOLOR);
		} else {
			label.setBorder(null);
		}

		return label;
	}

}
