package org.jdmp.gui.variable;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.AbstractTableModel;

import org.jdmp.core.variable.HasVariables;
import org.jdmp.core.variable.Variable;

public class VariableListTableModel extends AbstractTableModel implements ListDataListener {
	private static final long serialVersionUID = -1032855724069297926L;

	public static final int ICONCOLUMN = 0;

	public static final int LABELCOLUMN = 1;

	public static final int SIZECOLUMN = 2;

	public static final int MATRIXCOLUMN = 3;

	public static final int HISTORYCOLUMN = 4;

	public static final int MATRIXCOUNTCOLUMN = 5;

	public static final int MEMORYSIZECOLUMN = 6;

	private HasVariables iVariables = null;

	public VariableListTableModel(HasVariables iVariables) {
		this.iVariables = iVariables;
		iVariables.getVariableList().addListDataListener(this);
	}

	public int getRowCount() {
		return iVariables.getVariableList().getSize();
	}

	public int getColumnCount() {
		return 7;
	}

	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case ICONCOLUMN:
			return "";
		case LABELCOLUMN:
			return "Label";
		case SIZECOLUMN:
			return "Size";
		case MATRIXCOLUMN:
			return "Matrix";
		case HISTORYCOLUMN:
			return "History";
		case MATRIXCOUNTCOLUMN:
			return "MatrixCount";
		case MEMORYSIZECOLUMN:
			return "MemorySize";
		default:
			return ("unknown");
		}
	}

	public Class<?> getColumnClass(int columnIndex) {
		return Variable.class;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return iVariables.getVariableList().getElementAt(rowIndex);
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		fireTableRowsUpdated(e.getIndex0(), e.getIndex1());
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		fireTableRowsInserted(e.getIndex0(), e.getIndex1());
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		fireTableRowsDeleted(e.getIndex0(), e.getIndex1());
	}

}
