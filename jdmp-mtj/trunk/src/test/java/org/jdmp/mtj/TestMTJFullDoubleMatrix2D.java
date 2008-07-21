package org.jdmp.mtj;

import org.ujmp.core.AbstractMatrixTest;
import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;

public class TestMTJFullDoubleMatrix2D extends AbstractMatrixTest {

	@Override
	public Matrix createMatrix(long... size) throws MatrixException {
		return new MTJDenseDoubleMatrix2D(size);
	}

	@Override
	public Matrix createMatrix(Matrix source) throws MatrixException {
		return new MTJDenseDoubleMatrix2D(source);
	}

}
