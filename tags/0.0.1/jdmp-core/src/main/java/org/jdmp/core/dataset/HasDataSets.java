package org.jdmp.core.dataset;

import java.util.List;

public interface HasDataSets {

	public void addDataSet(DataSet ds);

	public void removeDataSet(DataSet ds);

	public int getDataSetCount();

	public List<DataSet> getDataSetList();

	public DataSet getDataSet(int pos);

	public void addDataSetListListener(DataSetListListener l);

	public void removeDataSetListListener(DataSetListListener l);

	public int getIndexOfDataSet(DataSet ds);

	public void fireDataSetAdded(DataSetListEvent e);

	public void fireDataSetRemoved(DataSetListEvent e);

	public void fireDataSetUpdated(DataSetListEvent e);
}
