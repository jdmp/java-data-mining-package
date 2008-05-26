package org.jdmp.gui.sample.actions;

import javax.swing.JComponent;
import javax.swing.JMenuItem;

import org.jdmp.core.sample.Sample;
import org.jdmp.core.sample.WeightedSample;
import org.jdmp.gui.actions.ObjectActions;

public class SampleActions extends ObjectActions {
	private static final long serialVersionUID = -8953849580621988673L;

	public SampleActions(JComponent c, Sample p) {
		super(c, p);

		if (p instanceof WeightedSample) {
			add(new JMenuItem(new DivideInputAction(c, (WeightedSample) p)));
		}

	}

}
