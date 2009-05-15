package org.jdmp.core.dataset;

import org.jdmp.core.util.ObservableMap;

public interface HasDataSetMap extends HasDataSets {

	public ObservableMap<DataSet> getDataSets();

}
