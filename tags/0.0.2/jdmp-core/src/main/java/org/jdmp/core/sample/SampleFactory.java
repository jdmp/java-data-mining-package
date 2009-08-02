package org.jdmp.core.sample;

public abstract class SampleFactory {

	public static final Sample labeledSample(String label) {
		Sample s = new DefaultSample(label);
		return s;
	}

	public static final Sample emptySample() {
		Sample s = new DefaultSample();
		return s;
	}

}
