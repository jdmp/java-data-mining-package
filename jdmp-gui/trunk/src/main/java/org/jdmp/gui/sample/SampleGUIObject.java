package org.jdmp.gui.sample;

import org.jdmp.core.sample.Sample;
import org.jdmp.gui.AbstractGUIObject;

public class SampleGUIObject extends AbstractGUIObject {
	private static final long serialVersionUID = -3436220704455373493L;

	private Sample sample = null;

	public SampleGUIObject(Sample s) {
		this.sample = s;
	}

	@Override
	public void clear() {
		sample.clear();
	}

	public String getLongStatus() {
		return sample.getLongStatus();
	}

	public String getShortStatus() {
		return sample.getShortStatus();
	}

	public void dispose() {
		sample.dispose();
	}

	public Sample getSample() {
		return sample;
	}

}
