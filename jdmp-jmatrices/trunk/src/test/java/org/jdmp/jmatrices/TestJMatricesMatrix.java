package org.jdmp.jmatrices;

import org.jdmp.jmatrices.JMatricesMatrix;
import org.ujmp.core.AbstractMatrixTest;
import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;

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
