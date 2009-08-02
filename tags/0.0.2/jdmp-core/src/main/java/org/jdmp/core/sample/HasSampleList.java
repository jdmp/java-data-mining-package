package org.jdmp.core.sample;

import org.jdmp.core.util.ObservableList;

public interface HasSampleList extends HasSamples {

	public ObservableList<Sample> getSampleList();

}
