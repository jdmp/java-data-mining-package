package org.jdmp.gui.sample.actions;

import javax.swing.JComponent;

import org.jdmp.core.sample.HasSamples;
import org.jdmp.gui.actions.ObjectAction;
import org.jdmp.matrix.interfaces.GUIObject;

public abstract class SampleListAction extends ObjectAction {

	public SampleListAction(JComponent c, HasSamples p) {
		super(c, (GUIObject) p);
	}

	public HasSamples getISamples() {
		return (HasSamples) getObject();
	}
}
