package org.jdmp.vecmath;

import org.jdmp.matrix.AbstractMatrixTest;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.vecmath.VecMathMatrix;

public class TestVecMathMatrix extends AbstractMatrixTest {

	@Override
	public Matrix createMatrix(long... size) throws MatrixException {
		return new VecMathMatrix(size);
	}

	@Override
	public Matrix createMatrix(Matrix source) throws MatrixException {
		return new VecMathMatrix(source);
	}

}
