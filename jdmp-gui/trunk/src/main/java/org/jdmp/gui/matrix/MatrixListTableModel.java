package org.jdmp.gui.matrix;

import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.VariableEvent;
import org.jdmp.core.variable.VariableListener;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.collections.MatrixList;
import org.jdmp.matrix.interfaces.HasMatrixList;

public class MatrixListTableModel extends AbstractTableModel implements VariableListener, HasMatrixList {
	private static final long serialVersionUID = 1820859033991171760L;

	private HasMatrixList variable = null;

	public static final int INDEXCOLUMN = 0;

	public static final int MATRIXCOLUMN = 1;

	public static final int MATRIXPLOTCOLUMN = 2;


	public static final int SIZECOLUMN = 3;

	public static final int LABELCOLUMN = 4;

	public MatrixListTableModel(HasMatrixList v) {
		this.variable = v;
		if (v instanceof Variable) {
			((Variable) variable).addVariableListener(this);
		}
	}

	public int getRowCount() {
		return variable.getMatrixList().size();
	}

	public int getColumnCount() {
		return 5;
	}

	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case INDEXCOLUMN:
			return "Index";
		case MATRIXCOLUMN:
			return "Matrix";
		case MATRIXPLOTCOLUMN:
			return "Plot";
		case SIZECOLUMN:
			return "Size";
		case LABELCOLUMN:
			return "Label";
		default:
			return "x";
		}
	}

	public Class<?> getColumnClass(int columnIndex) {
		return MatrixGUIObject.class;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		MatrixGUIObject o = (MatrixGUIObject) variable.getMatrixList().get(rowIndex).getGUIObject();
		return o;
	}

	public void valueChanged(VariableEvent e) {
		switch (e.getType()) {
		case ALLUPDATED:
			super.fireTableDataChanged();
			break;
		case UPDATED:
			super.fireTableRowsUpdated(e.getIndex(), e.getIndex());
			break;
		case ADDED:
			super.fireTableRowsInserted(e.getIndex(), e.getIndex());
			break;
		case REMOVED:
			super.fireTableRowsDeleted(e.getIndex(), e.getIndex());
			break;
		}
	}

	public ListSelectionModel getRowSelectionModel() {
		return variable.getRowSelectionModel();
	}

	public ListSelectionModel getColumnSelectionModel() {
		return variable.getColumnSelectionModel();
	}

	public void fireValueChanged(Matrix m) {
	}

	public int getMatrixCount() {
		return variable.getMatrixCount();
	}

	public MatrixList getMatrixList() {
		return variable.getMatrixList();
	}

	public Matrix getMatrix(int index) {
		return variable.getMatrix(index);
	}

}
