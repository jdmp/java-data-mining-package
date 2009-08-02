package org.jdmp.core.sample;

import java.util.EventListener;


public interface SampleListListener extends EventListener {

	public void sampleUpdated(SampleListEvent e);

	public void sampleAdded(SampleListEvent e);

	public void sampleRemoved(SampleListEvent e);

	public void samplesShuffled(SampleListEvent e);

}
