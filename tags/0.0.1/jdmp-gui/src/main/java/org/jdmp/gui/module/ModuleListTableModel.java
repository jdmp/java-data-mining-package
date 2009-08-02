package org.jdmp.gui.module;

import javax.swing.table.AbstractTableModel;

import org.jdmp.core.module.HasModules;
import org.jdmp.core.module.Module;
import org.jdmp.core.module.ModuleEvent;
import org.jdmp.core.module.ModuleListEvent;
import org.jdmp.core.module.ModuleListListener;
import org.jdmp.core.module.ModuleListener;

public class ModuleListTableModel extends AbstractTableModel implements ModuleListener, ModuleListListener {
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
		iModules.addModuleListListener(this);
		for (Module i : iModules.getModuleList())
			i.addModuleListener(this);
	}

	public int getRowCount() {
		return iModules.getModuleCount();
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
		Module i = iModules.getModule(rowIndex);
		return i;
	}

	public void nextDataSetSelected(ModuleEvent e) {
		fireTableDataChanged();
	}

	public void nextModuleSelected(ModuleEvent e) {
		fireTableDataChanged();
	}

	public void epochIncreased(ModuleEvent e) {
		fireTableDataChanged();
	}

	public void variableAdded(ModuleEvent e) {
		fireTableDataChanged();
	}

	public void variableDeleted(ModuleEvent e) {
		fireTableDataChanged();
	}

	public void clientConnected(ModuleEvent e) {
		fireTableDataChanged();
	}

	public void clientDisconnected(ModuleEvent e) {
		fireTableDataChanged();
	}

	public void connected(ModuleEvent e) {
		fireTableDataChanged();
	}

	public void disconnected(ModuleEvent e) {
		fireTableDataChanged();
	}

	public void moduleAdded(ModuleListEvent e) {
		Module i = (Module) e.getData();
		i.addModuleListener(this);
		fireTableDataChanged();
	}

	public void moduleRemoved(ModuleListEvent e) {
		Module i = (Module) e.getData();
		i.removeModuleListener(this);
		fireTableDataChanged();
	}

	public void moduleUpdated(ModuleListEvent e) {
		Module i = (Module) e.getData();
		fireTableDataChanged();
	}
}
