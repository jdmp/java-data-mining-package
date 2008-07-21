package org.jdmp.vecmath;

import org.jdmp.vecmath.VecMathMatrix;
import org.ujmp.core.AbstractMatrixTest;
import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;

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
