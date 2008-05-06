package org.jdmp.matrix.interfaces;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixList;

public interface HasSourceMatrix {

	public Matrix getSourceMatrix();

	public MatrixList getSourceMatrices();
}
