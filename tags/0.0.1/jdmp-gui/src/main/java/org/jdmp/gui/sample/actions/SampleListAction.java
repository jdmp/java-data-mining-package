package org.jdmp.gui.sample.actions;

import javax.swing.JComponent;

import org.jdmp.core.sample.HasSamples;
import org.jdmp.core.util.AbstractGUIObject;
import org.jdmp.gui.actions.ObjectAction;

public abstract class SampleListAction extends ObjectAction {

	public SampleListAction(JComponent c, HasSamples p) {
		super(c, (AbstractGUIObject) p);
	}

	public HasSamples getISamples() {
		return (HasSamples) getObject();
	}
}
