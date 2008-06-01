package org.jdmp.jama;

import org.jdmp.jama.JamaMatrix;
import org.jdmp.matrix.AbstractMatrixTest;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.exceptions.MatrixException;

public class TestJamaMatrix extends AbstractMatrixTest {

	@Override
	public Matrix createMatrix(long... size) throws MatrixException {
		return new JamaMatrix(size);
	}

	@Override
	public Matrix createMatrix(Matrix source) throws MatrixException {
		return new JamaMatrix(source);
	}

}
