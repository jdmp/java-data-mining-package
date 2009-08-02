package org.jdmp.gui.sample.actions;

import javax.swing.JComponent;

import org.jdmp.core.sample.HasSampleList;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.gui.actions.ObjectAction;

public abstract class SampleListAction extends ObjectAction {

	public SampleListAction(JComponent c, HasSampleList p) {
		super(c, (GUIObject) p);
	}

	public HasSampleList getISamples() {
		return (HasSampleList) getObject();
	}
}
