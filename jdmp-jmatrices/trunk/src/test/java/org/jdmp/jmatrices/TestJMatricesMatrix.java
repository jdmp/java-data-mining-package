package org.jdmp.jmatrices;

import org.jdmp.jmatrices.JMatricesMatrix;
import org.jdmp.matrix.AbstractMatrixTest;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.exceptions.MatrixException;

public class TestJMatricesMatrix extends AbstractMatrixTest {

	@Override
	public Matrix createMatrix(long... size) throws MatrixException {
		return new JMatricesMatrix(size);
	}

	@Override
	public Matrix createMatrix(Matrix source) throws MatrixException {
		return new JMatricesMatrix(source);
	}

}
