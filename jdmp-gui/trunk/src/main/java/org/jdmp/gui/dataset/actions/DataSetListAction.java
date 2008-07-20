package org.jdmp.gui.dataset.actions;

import javax.swing.JComponent;

import org.jdmp.core.dataset.HasDataSets;
import org.jdmp.gui.actions.ObjectAction;
import org.ujmp.core.interfaces.GUIObject;

public abstract class DataSetListAction extends ObjectAction {

	public DataSetListAction(JComponent c, HasDataSets iDataSets) {
		super(c, (GUIObject) iDataSets);
	}

	public HasDataSets getIDataSets() {
		return (HasDataSets) getObject();
	}

}
