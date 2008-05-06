package org.jdmp.matrix.implementations;

import org.jdmp.matrix.AbstractMatrixTest;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.implementations.basic.DefaultDenseDoubleMatrix2D;

public class TestDefaultFullDoubleMatrix2D extends AbstractMatrixTest {

	@Override
	public Matrix createMatrix(long... size) throws MatrixException {
		return new DefaultDenseDoubleMatrix2D(size);
	}

	@Override
	public Matrix createMatrix(Matrix source) throws MatrixException {
		return new DefaultDenseDoubleMatrix2D(source);
	}

}
