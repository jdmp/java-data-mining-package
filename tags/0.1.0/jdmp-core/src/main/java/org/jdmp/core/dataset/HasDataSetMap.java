package org.jdmp.core.dataset;

import org.jdmp.core.util.DefaultObservableMap;

public interface HasDataSetMap extends HasDataSets {

	public DefaultObservableMap<DataSet> getDataSets();

}
