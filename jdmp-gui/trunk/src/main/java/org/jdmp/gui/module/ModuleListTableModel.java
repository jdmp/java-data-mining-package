package org.jdmp.gui.module;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.AbstractTableModel;

import org.jdmp.core.module.HasModuleList;
import org.jdmp.core.module.Module;

public class ModuleListTableModel extends AbstractTableModel implements ListDataListener {
	private static final long serialVersionUID = -1372599464095754625L;

	public static final int ICONCOLUMN = 0;

	public static final int LABELCOLUMN = 1;

	public static final int ALGORITHMCOUNTCOLUMN = 2;

	public static final int VARIABLECOUNTCOLUMN = 3;

	public static final int MODULECOUNTCOLUMN = 4;

	public static final int DATASETCOUNTCOLUMN = 5;

	private HasModuleList iModules = null;

	public ModuleListTableModel(HasModuleList iModules) {
		this.iModules = iModules;
		iModules.getModuleList().addListDataListener(this);
	}

	public int getRowCount() {
		return iModules.getModuleList().getSize();
	}

	public int getColumnCount() {
		return 6;
	}

	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case ICONCOLUMN:
			return "";
		case LABELCOLUMN:
			return "Label";
		case VARIABLECOUNTCOLUMN:
			return "VariableCount";
		case MODULECOUNTCOLUMN:
			return "ModuleCount";
		case DATASETCOUNTCOLUMN:
			return "DataSetCount";
		case ALGORITHMCOUNTCOLUMN:
			return "AlgorithmCount";
		default:
			return "unknown";

		}
	}

	public Class<?> getColumnClass(int columnIndex) {
		return Module.class;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return iModules.getModuleList().getElementAt(rowIndex);
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
