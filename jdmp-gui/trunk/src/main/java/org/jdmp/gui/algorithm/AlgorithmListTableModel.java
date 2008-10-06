package org.jdmp.gui.algorithm;

import javax.swing.table.AbstractTableModel;

import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.algorithm.HasAlgorithms;

public class AlgorithmListTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -3779798282436020436L;

	public static final int ICONCOLUMN = 0;

	public static final int LABELCOLUMN = 1;

	public static final int VARIABLECOLUMN = 2;

	public static final int ALGORITHMLISTCOLUMN = 3;

	private HasAlgorithms iAlgorithms = null;

	public AlgorithmListTableModel(HasAlgorithms iAlgorithms) {
		this.iAlgorithms = iAlgorithms;
		// iAlgorithms.addAlgorithmListListener(this);
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
		switch (columnIndex) {
		default:
			return Algorithm.class;
		}
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		default:
			Algorithm a = iAlgorithms.getAlgorithmList().getElementAt(rowIndex);
			return a;
		}
	}

}
