package org.jdmp.gui.sample.actions;

import javax.swing.JComponent;

import org.jdmp.gui.sample.SampleGUIObject;
import org.ujmp.gui.actions.ObjectAction;

public abstract class SampleAction extends ObjectAction {

	public SampleAction(JComponent c, SampleGUIObject p) {
		super(c, p);
	}

	public SampleGUIObject getSample() {
		return (SampleGUIObject) getObject();
	}
}
