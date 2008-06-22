package org.jdmp.gui.dataset;

import javax.swing.table.AbstractTableModel;

import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.dataset.DataSetEvent;
import org.jdmp.core.dataset.DataSetListEvent;
import org.jdmp.core.dataset.DataSetListListener;
import org.jdmp.core.dataset.DataSetListener;
import org.jdmp.core.dataset.HasDataSets;

public class DataSetListTableModel extends AbstractTableModel implements DataSetListListener,
		DataSetListener {
	private static final long serialVersionUID = 8897049622154020275L;

	public static final int ICONCOLUMN = 0;

	public static final int LABELCOLUMN = 1;

	public static final int SAMPLECOUNTCOLUMN = 2;

	public static final int VARIABLECOLUMN = 3;

	private HasDataSets iDataSets = null;

	public DataSetListTableModel(HasDataSets iDataSets) {
		this.iDataSets = iDataSets;
		iDataSets.addDataSetListListener(this);
		for (DataSet ds : iDataSets.getDataSetList()) {
			ds.addDataSetListener(this);
		}
	}

	public int getRowCount() {
		return iDataSets.getDataSetCount();
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
		DataSet d = iDataSets.getDataSet(rowIndex);
		return d;
	}

	public void dataSetShuffled(DataSetEvent e) {
		int row = iDataSets.getIndexOfDataSet((DataSet) e.getSource());
		if (row >= 0)
			fireTableRowsUpdated(row, row);
	}

	public void dataSetAdded(DataSetListEvent e) {
		DataSet ds = (DataSet) e.getData();
		ds.addDataSetListener(this);
		fireTableDataChanged();
	}

	public void dataSetUpdated(DataSetListEvent e) {
		int row = iDataSets.getIndexOfDataSet((DataSet) e.getData());
		if (row >= 0)
			fireTableDataChanged();
	}

	public void dataSetRemoved(DataSetListEvent e) {
		DataSet ds = (DataSet) e.getData();
		ds.removeDataSetListener(this);
		fireTableDataChanged();
	}
}
