package org.jdmp.gui.matrix.actions;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import org.jdmp.core.matrix.wrappers.MatrixListToMatrixWrapper;
import org.jdmp.gui.actions.ObjectAction;
import org.jdmp.gui.matrix.MatrixGUIObject;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.calculation.Calculation.Ret;
import org.jdmp.matrix.interfaces.HasMatrixList;

public abstract class MatrixAction extends ObjectAction {

	public static final int ROW = Matrix.ROW;

	public static final int COLUMN = Matrix.COLUMN;

	public static final int ALL = Matrix.ALL;

	private HasMatrixList variable = null;

	public MatrixAction(JComponent c, MatrixGUIObject matrix, HasMatrixList v) {
		super(c, matrix);
		this.variable = v;
	}

	public int getDimension() {
		int dimension = -1;

		if (dimension == -1) {
			if (getMatrixObject().getColumnCount() == 1) {
				dimension = ROW;
			} else if (getMatrixObject().getRowCount() == 1) {
				dimension = COLUMN;
			}
		}
		if (dimension == -1) {
			int i = JOptionPane.showOptionDialog(getComponent(), "Dimension", "Select Dimension",
					JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[] { "Row", "Column", "All" },
					"Row");
			if (i == 2) {
				dimension = ALL;
			} else {
				dimension = i;
			}
		}
		return dimension;
	}

	public MatrixGUIObject getMatrixObject() {
		MatrixGUIObject m = (MatrixGUIObject) getObject();
		if (m != null) {
			int startRow = m.getRowSelectionModel().getMinSelectionIndex();
			int endRow = m.getRowSelectionModel().getMaxSelectionIndex();
			int startColumn = m.getColumnSelectionModel().getMinSelectionIndex();
			int endColumn = m.getColumnSelectionModel().getMaxSelectionIndex();
			if (startRow != -1 && startColumn != -1 && (startRow != endRow || startColumn != endColumn)) {
				m = (MatrixGUIObject) m.getMatrix().subMatrix(Ret.LINK, startRow, startColumn, endRow, endColumn)
						.getGUIObject();
			}
			return m;
		} else {
			int min = variable.getRowSelectionModel().getMinSelectionIndex();
			int max = variable.getRowSelectionModel().getMaxSelectionIndex();
			Matrix all = new MatrixListToMatrixWrapper(variable);

			if (min >= 0 && max >= 0) {
				Matrix selection = all.subMatrix(Ret.LINK, min, 0, max, all.getColumnCount() - 1);
				return (MatrixGUIObject) selection.getGUIObject();
			} else {
				return (MatrixGUIObject) all.getGUIObject();
			}
		}
	}

	public void setMatrix(MatrixGUIObject m) {
		setObject(m);
	}

	public HasMatrixList getVariable() {
		return variable;
	}

}
