package org.jdmp.mtj;

import org.jdmp.matrix.AbstractMatrixTest;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;

public class TestMTJFullDoubleMatrix2D extends AbstractMatrixTest {

	@Override
	public Matrix createMatrix(long... size) throws MatrixException {
		return new MTJFullDoubleMatrix2D(size);
	}

	@Override
	public Matrix createMatrix(Matrix source) throws MatrixException {
		return new MTJFullDoubleMatrix2D(source);
	}

}
