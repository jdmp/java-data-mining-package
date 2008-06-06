package org.jdmp.gui.dataset;

import org.jdmp.gui.util.AbstractFrame;
import org.jdmp.matrix.exceptions.MatrixException;

public class DataSetFrame extends AbstractFrame {
	private static final long serialVersionUID = 4488466762680050937L;

	public DataSetFrame(DataSetGUIObject ds) throws MatrixException {
		super(ds);
	}

}
