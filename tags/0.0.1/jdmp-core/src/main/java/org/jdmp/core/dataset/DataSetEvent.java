package org.jdmp.core.dataset;

import java.util.EventObject;

public class DataSetEvent extends EventObject {
	private static final long serialVersionUID = -6398991182604935015L;

	public DataSetEvent(DataSet source) {
		super(source);
	}
}
