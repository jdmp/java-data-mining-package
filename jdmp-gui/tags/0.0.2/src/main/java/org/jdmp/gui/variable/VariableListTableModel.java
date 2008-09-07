package org.jdmp.gui.variable;

import javax.swing.table.AbstractTableModel;

import org.jdmp.core.variable.HasVariables;
import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.VariableEvent;
import org.jdmp.core.variable.VariableListener;

public class VariableListTableModel extends AbstractTableModel implements VariableListener {
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
		for (Variable v : iVariables.getVariableList()) {
			if (v != null) {
				v.addVariableListener(this);
			}
		}
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
		switch (columnIndex) {
		default:
			return Variable.class;
		}
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Variable v = iVariables.getVariableList().getElementAt(rowIndex);
		switch (columnIndex) {
		default:
			return v;
		}
	}

	public void valueChanged(VariableEvent e) {
		int row = iVariables.getVariableList().indexOf((Variable) e.getSource());
		if (row >= 0) {
			fireTableRowsUpdated(row, row);
		}
	}

}
