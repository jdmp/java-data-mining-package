package org.jdmp.gui.sample.actions;

import javax.swing.Action;
import javax.swing.JComponent;

import org.jdmp.core.sample.ClassificationSample;
import org.jdmp.core.sample.HasSamples;

public class AddSampleAction extends SampleListAction {
	private static final long serialVersionUID = 3370112393848013976L;

	public AddSampleAction(JComponent c, HasSamples p) {
		super(c, p);
		putValue(Action.NAME, "Add sample");
		putValue(Action.SHORT_DESCRIPTION, "Add a sample");
	}

	public Object call() {
		getISamples().addSample(new ClassificationSample());
		return null;
	}

}
