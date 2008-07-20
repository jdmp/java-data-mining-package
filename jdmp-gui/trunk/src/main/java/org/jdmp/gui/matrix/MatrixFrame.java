package org.jdmp.gui.matrix;

import org.jdmp.gui.util.AbstractFrame;
import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;

public class MatrixFrame extends AbstractFrame {
	private static final long serialVersionUID = -3705093197648545721L;

	public MatrixFrame(MatrixGUIObject m) throws MatrixException {
		super(m);
	}

	public MatrixFrame(Matrix m) throws MatrixException {
		super((MatrixGUIObject) m.getGUIObject());
	}

}
