package org.jdmp.gui.dataset;

import org.jdmp.core.dataset.DataSet;
import org.jdmp.gui.util.AbstractFrame;
import org.jdmp.matrix.MatrixException;

public class DataSetFrame extends AbstractFrame {
	private static final long serialVersionUID = 4488466762680050937L;

	public DataSetFrame(DataSet ds) throws MatrixException {
		super(ds);
	}

}
