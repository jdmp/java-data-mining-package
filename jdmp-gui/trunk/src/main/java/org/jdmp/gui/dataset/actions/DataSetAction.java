package org.jdmp.gui.dataset.actions;

import javax.swing.JComponent;

import org.jdmp.gui.actions.ObjectAction;
import org.jdmp.gui.dataset.DataSetGUIObject;

public abstract class DataSetAction extends ObjectAction {

	public DataSetAction(JComponent c, DataSetGUIObject ds) {
		super(c, ds);
	}

	public DataSetGUIObject getDataSet() {
		return (DataSetGUIObject) getObject();
	}

}
