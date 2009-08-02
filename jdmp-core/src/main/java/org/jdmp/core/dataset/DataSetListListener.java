package org.jdmp.core.dataset;

import java.util.EventListener;

public interface DataSetListListener extends EventListener {

	public void dataSetAdded(DataSetListEvent e);

	public void dataSetRemoved(DataSetListEvent e);

	public void dataSetUpdated(DataSetListEvent e);
}
