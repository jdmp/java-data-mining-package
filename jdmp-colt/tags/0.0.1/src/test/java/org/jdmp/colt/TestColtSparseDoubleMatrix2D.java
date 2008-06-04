package org.jdmp.colt;

import org.jdmp.colt.ColtSparseDoubleMatrix2D;
import org.jdmp.matrix.AbstractMatrixTest;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.exceptions.MatrixException;

public class TestColtSparseDoubleMatrix2D extends AbstractMatrixTest {

	@Override
	public Matrix createMatrix(long... size) throws MatrixException {
		return new ColtSparseDoubleMatrix2D(size);
	}

	@Override
	public Matrix createMatrix(Matrix source) throws MatrixException {
		return new ColtSparseDoubleMatrix2D(source);
	}

}