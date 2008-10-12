package org.jdmp.core.dataset;

import org.jdmp.core.util.ObservableList;

public interface HasDataSetList extends HasDataSets {

	public ObservableList<DataSet> getDataSetList();
}
