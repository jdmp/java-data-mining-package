package org.jdmp.gui.dataset;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.AbstractTableModel;

import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.dataset.HasDataSets;

public class DataSetListTableModel extends AbstractTableModel implements ListDataListener {
	private static final long serialVersionUID = 8897049622154020275L;

	public static final int ICONCOLUMN = 0;

	public static final int LABELCOLUMN = 1;

	public static final int SAMPLECOUNTCOLUMN = 2;

	public static final int VARIABLECOLUMN = 3;

	private HasDataSets iDataSets = null;

	public DataSetListTableModel(HasDataSets iDataSets) {
		this.iDataSets = iDataSets;
		iDataSets.getDataSetList().addListDataListener(this);
	}

	public int getRowCount() {
		return iDataSets.getDataSetList().getSize();
	}

	public int getColumnCount() {
		return 4;
	}

	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case ICONCOLUMN:
			return "";
		case LABELCOLUMN:
			return "Label";
		case SAMPLECOUNTCOLUMN:
			return "SampleCount";
		case VARIABLECOLUMN:
			return "Variables";
		default:
			return "unknown";
		}
	}

	public Class<?> getColumnClass(int columnIndex) {
		return DataSet.class;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return iDataSets.getDataSetList().getElementAt(rowIndex);
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
