package org.jdmp.gui.dataset;

import org.jdmp.core.JDMPCoreObject;
import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.dataset.HasDataSets;
import org.jdmp.gui.util.AbstractListPanel;
import org.ujmp.core.interfaces.GUIObject;

public class DataSetListPanel extends AbstractListPanel {
	private static final long serialVersionUID = -3012562710674803164L;

	public DataSetListPanel(HasDataSets iDataSets) {
		super();
		if (iDataSets instanceof JDMPCoreObject) {
			this.object = ((JDMPCoreObject) iDataSets).getGUIObject();
		} else {
			this.object = (GUIObject) iDataSets;
		}

		dataModel = new DataSetListTableModel(iDataSets);
		dataModel.addTableModelListener(this);
		jTable.setDefaultRenderer(DataSet.class, new DataSetTableCellRenderer());

		jTable.setModel(dataModel);

		updateTitle();
	}

	
	public void updateTitle() {
		getBorder().setTitle("DataSets (" + jTable.getRowCount() + ")");
		repaint(1000);
	}

}
