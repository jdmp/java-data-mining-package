package org.jdmp.gui.sample.actions;

import javax.swing.Action;
import javax.swing.JComponent;

import org.jdmp.core.sample.ClassificationSample;
import org.jdmp.core.sample.HasSampleList;

public class AddSampleAction extends SampleListAction {
	private static final long serialVersionUID = 3370112393848013976L;

	public AddSampleAction(JComponent c, HasSampleList p) {
		super(c, p);
		putValue(Action.NAME, "Add sample");
		putValue(Action.SHORT_DESCRIPTION, "Add a sample");
	}

	public Object call() {
		getISamples().getSampleList().add(new ClassificationSample());
		return null;
	}

}
