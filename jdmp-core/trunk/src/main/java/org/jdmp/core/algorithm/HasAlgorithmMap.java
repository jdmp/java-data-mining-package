package org.jdmp.core.algorithm;

import org.jdmp.core.util.ObservableMap;

public interface HasAlgorithmMap extends HasAlgorithms {

	public ObservableMap<Algorithm> getAlgorithmList();

}
