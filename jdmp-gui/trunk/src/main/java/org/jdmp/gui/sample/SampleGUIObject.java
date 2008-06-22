package org.jdmp.gui.sample;

import javax.swing.Icon;

import org.jdmp.core.sample.Sample;
import org.jdmp.gui.AbstractGUIObject;

public class SampleGUIObject extends AbstractGUIObject {
	private static final long serialVersionUID = -3436220704455373493L;

	private Sample sample = null;

	public SampleGUIObject(Sample s) {
		this.sample = s;
	}

	public void clear() {
	}

	public Sample getSample() {
		return sample;
	}

	public Icon getIcon() {
		return null;
	}

	public String getLabel() {
		return sample.getLabel();
	}

	public void setLabel(String label) {
		sample.setLabel(label);
	}

	public String getDescription() {
		return sample.getDescription();
	}

	public void setDescription(String description) {
		sample.setDescription(description);
	}

	public String toString() {
		return sample.toString();
	}

}
