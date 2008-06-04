package org.jdmp.core.sample;

import java.util.List;

public interface HasSamples {

	public void addSample(Sample sample);

	public int getSampleCount();

	public Sample getSample(int pos);

	public List<Sample> getSampleList();

	public void removeSample(Sample sample);

	public void addSampleListListener(SampleListListener l);

	public void removeSampleListListener(SampleListListener l);

	public int getIndexOfSample(Sample sample);

	public void fireSampleAdded(SampleListEvent e);

	public void fireSampleRemoved(SampleListEvent e);

	public void fireSampleUpdated(SampleListEvent e);

}
