package org.jdmp.gui.dataset;

import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.gui.util.AbstractFrame;

public class DataSetFrame extends AbstractFrame {
	private static final long serialVersionUID = 4488466762680050937L;

	public DataSetFrame(DataSetGUIObject ds) throws MatrixException {
		super(ds, new DataSetPanel(ds));
	}

}
