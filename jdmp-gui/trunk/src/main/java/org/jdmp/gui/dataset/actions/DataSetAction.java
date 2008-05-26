package org.jdmp.gui.dataset.actions;

import javax.swing.JComponent;

import org.jdmp.core.dataset.DataSet;
import org.jdmp.gui.actions.ObjectAction;

public abstract class DataSetAction extends ObjectAction {

	public DataSetAction(JComponent c, DataSet ds) {
		super(c, ds);
	}

	public DataSet getDataSet() {
		return (DataSet) getObject();
	}

}
