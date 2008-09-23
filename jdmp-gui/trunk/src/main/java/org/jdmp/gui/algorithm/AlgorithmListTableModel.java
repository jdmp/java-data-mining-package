package org.jdmp.gui.algorithm;

import javax.swing.table.AbstractTableModel;

import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.algorithm.AlgorithmEvent;
import org.jdmp.core.algorithm.AlgorithmListEvent;
import org.jdmp.core.algorithm.AlgorithmListListener;
import org.jdmp.core.algorithm.AlgorithmListener;
import org.jdmp.core.algorithm.HasAlgorithms;

public class AlgorithmListTableModel extends AbstractTableModel implements AlgorithmListener,
		AlgorithmListListener {
	private static final long serialVersionUID = -3779798282436020436L;

	public static final int ICONCOLUMN = 0;

	public static final int LABELCOLUMN = 1;

	public static final int VARIABLECOLUMN = 2;

	public static final int ALGORITHMLISTCOLUMN = 3;

	private HasAlgorithms iAlgorithms = null;

	public AlgorithmListTableModel(HasAlgorithms iAlgorithms) {
		this.iAlgorithms = iAlgorithms;
		iAlgorithms.addAlgorithmListListener(this);
		for (Algorithm a : iAlgorithms.getAlgorithmList()) {
			if (a != null) {
				a.addAlgorithmListener(this);
			}
		}
	}

	public int getRowCount() {
		return iAlgorithms.getAlgorithmCount();
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
		switch (columnIndex) {
		default:
			return Algorithm.class;
		}
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		default:
			Algorithm a = iAlgorithms.getAlgorithm(rowIndex);
			return a;
		}
	}

	public void algorithmCountIncreased(AlgorithmEvent e) {
		int row = iAlgorithms.getIndexOfAlgorithm((Algorithm) e.getSource());
		if (row >= 0)
			fireTableRowsUpdated(row, row);
	}

	public void algorithmAdded(AlgorithmListEvent e) {
		Algorithm a = (Algorithm) e.getData();
		a.addAlgorithmListener(this);
		fireTableDataChanged();
	}

	public void algorithmRemoved(AlgorithmListEvent e) {
		Algorithm a = (Algorithm) e.getData();
		a.removeAlgorithmListener(this);
		fireTableDataChanged();
	}

	public void algorithmUpdated(AlgorithmListEvent e) {
		int row = iAlgorithms.getIndexOfAlgorithm((Algorithm) e.getData());
		if (row >= 0)
			fireTableRowsUpdated(row, row);
	}
}
