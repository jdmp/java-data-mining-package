package org.jdmp.gui.algorithm;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.AbstractTableModel;

import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.algorithm.HasAlgorithms;

public class AlgorithmListTableModel extends AbstractTableModel implements ListDataListener {
	private static final long serialVersionUID = -3779798282436020436L;

	public static final int ICONCOLUMN = 0;

	public static final int LABELCOLUMN = 1;

	public static final int VARIABLECOLUMN = 2;

	public static final int ALGORITHMLISTCOLUMN = 3;

	private HasAlgorithms iAlgorithms = null;

	public AlgorithmListTableModel(HasAlgorithms iAlgorithms) {
		this.iAlgorithms = iAlgorithms;
		iAlgorithms.getAlgorithmList().addListDataListener(this);
	}

	public int getRowCount() {
		return iAlgorithms.getAlgorithmList().getSize();
	}

	public int getColumnCount() {
		return 4;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case ICONCOLUMN:
			return "";
		case VARIABLECOLUMN:
			return "Variables";
		case ALGORITHMLISTCOLUMN:
			return "Algorithms";
		case LABELCOLUMN:
			return "Label";
		default:
			return "x";
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return Algorithm.class;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return iAlgorithms.getAlgorithmList().getElementAt(rowIndex);
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
