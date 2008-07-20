package org.jdmp.gui.clipboard;

import java.awt.datatransfer.DataFlavor;

import org.ujmp.core.Matrix;

public class MatrixFlavor extends DataFlavor {
	private static final long serialVersionUID = 5009552508530097153L;

	public static final DataFlavor matrixFlavor = new MatrixFlavor();

	public MatrixFlavor() {
		super(Matrix.class, "Matrix");
	}

}
