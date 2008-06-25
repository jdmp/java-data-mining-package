package org.jdmp.gui.module;

import javax.swing.table.AbstractTableModel;

import org.jdmp.core.module.HasModules;
import org.jdmp.core.module.Module;

public class ModuleListTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -1372599464095754625L;

	public static final int ICONCOLUMN = 0;

	public static final int LABELCOLUMN = 1;

	public static final int ALGORITHMCOUNTCOLUMN = 2;

	public static final int VARIABLECOUNTCOLUMN = 3;

	public static final int MODULECOUNTCOLUMN = 4;

	public static final int DATASETCOUNTCOLUMN = 5;

	private HasModules iModules = null;

	public ModuleListTableModel(HasModules iModules) {
		this.iModules = iModules;
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
		Module i = iModules.getModuleList().getElementAt(rowIndex);
		return i;
	}

}
