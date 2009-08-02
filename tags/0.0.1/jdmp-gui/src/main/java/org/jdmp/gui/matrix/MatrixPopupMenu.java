package org.jdmp.gui.matrix;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

import org.jdmp.core.matrix.MatrixGUIObject;
import org.jdmp.gui.actions.ObjectActions;
import org.jdmp.gui.matrix.actions.MatrixActions;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.calculation.Calculation.Ret;

public class MatrixPopupMenu extends JPopupMenu {
	private static final long serialVersionUID = -5501347047922058729L;

	public MatrixPopupMenu(JComponent c, MatrixGUIObject matrix, int row, int column) {
		JLabel label = new JLabel();
		label.setForeground(new Color(0, 0, 255));
		ObjectActions actions = null;

		if (matrix.getRowSelectionModel().isSelectedIndex(row)
				&& matrix.getColumnSelectionModel().isSelectedIndex(column)) {
			int startX = matrix.getColumnSelectionModel().getMinSelectionIndex();
			int endX = matrix.getColumnSelectionModel().getMaxSelectionIndex();
			int startY = matrix.getRowSelectionModel().getMinSelectionIndex();
			int endY = matrix.getRowSelectionModel().getMaxSelectionIndex();
			Matrix subMatrix = matrix.getMatrix().subMatrix(Ret.LINK, startY, startX, endY, endX);
			actions = new MatrixActions(c, (MatrixGUIObject) subMatrix.getGUIObject(), null);
			label.setText(" Selection " + subMatrix.getSize().toString().replaceAll(",", "x"));
		} else {
			actions = new MatrixActions(c, matrix, null);
			label.setText(" Matrix [" + matrix.getSize().toString().replaceAll(",", "x") + "]");
		}

		add(label);
		add(new JSeparator());

		for (JComponent jc : actions) {
			add(jc);
		}
	}
}
