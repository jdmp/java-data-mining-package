package org.jdmp.gui.sample.actions;

import javax.swing.JComponent;

import org.jdmp.core.sample.HasSampleList;
import org.jdmp.gui.actions.ObjectAction;
import org.ujmp.core.interfaces.GUIObject;

public abstract class SampleListAction extends ObjectAction {

	public SampleListAction(JComponent c, HasSampleList p) {
		super(c, (GUIObject) p);
	}

	public HasSampleList getISamples() {
		return (HasSampleList) getObject();
	}
}
