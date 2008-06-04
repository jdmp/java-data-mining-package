package org.jdmp.gui.sample.actions;

import javax.swing.JComponent;

import org.jdmp.core.sample.Sample;
import org.jdmp.gui.actions.ObjectAction;

public abstract class SampleAction extends ObjectAction {

	public SampleAction(JComponent c, Sample p) {
		super(c, p);
	}

	public Sample getSample() {
		return (Sample) getObject();
	}
}
