package org.jdmp.gui.dataset;

import javax.swing.Icon;

import org.jdmp.core.dataset.DataSet;
import org.jdmp.gui.AbstractGUIObject;

public class DataSetGUIObject extends AbstractGUIObject {
	private static final long serialVersionUID = -329942434062359920L;

	private DataSet dataSet = null;

	public DataSet getDataSet() {
		return dataSet;
	}

	public DataSetGUIObject(DataSet d) {
		this.dataSet = d;
	}

	public void clear() {
		dataSet.clear();
	}
	
	public Icon getIcon() {
		return null;
	}

}
