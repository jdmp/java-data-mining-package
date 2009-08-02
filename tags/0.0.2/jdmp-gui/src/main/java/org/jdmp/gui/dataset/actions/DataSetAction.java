package org.jdmp.gui.dataset.actions;

import javax.swing.JComponent;

import org.jdmp.gui.dataset.DataSetGUIObject;
import org.ujmp.gui.actions.ObjectAction;

public abstract class DataSetAction extends ObjectAction {

	public DataSetAction(JComponent c, DataSetGUIObject ds) {
		super(c, ds);
	}

	public DataSetGUIObject getDataSet() {
		return (DataSetGUIObject) getObject();
	}

}
