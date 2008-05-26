package org.jdmp.gui.dataset.actions;

import javax.swing.JComponent;

import org.jdmp.core.dataset.HasDataSets;
import org.jdmp.core.util.AbstractGUIObject;
import org.jdmp.gui.actions.ObjectAction;

public abstract class DataSetListAction extends ObjectAction {

	public DataSetListAction(JComponent c, HasDataSets iDataSets) {
		super(c, (AbstractGUIObject) iDataSets);
	}

	public HasDataSets getIDataSets() {
		return (HasDataSets) getObject();
	}

}
