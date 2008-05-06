package org.jdmp.test.matrix.implementations;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.implementations.basic.DefaultDenseObjectMatrix2D;
import org.jdmp.test.matrix.AbstractMatrixTest;

public class TestDefaultFullObjectMatrix2D extends AbstractMatrixTest {

	@Override
	public Matrix createMatrix(long... size) throws MatrixException {
		return new DefaultDenseObjectMatrix2D(size);
	}

	@Override
	public Matrix createMatrix(Matrix source) throws MatrixException {
		return new DefaultDenseObjectMatrix2D(source);
	}

}
