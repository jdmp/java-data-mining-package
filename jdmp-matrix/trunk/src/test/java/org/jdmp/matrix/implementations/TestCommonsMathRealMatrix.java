package org.jdmp.matrix.implementations;

import org.jdmp.matrix.AbstractMatrixTest;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.implementations.misc.CommonsMathRealMatrix;

public class TestCommonsMathRealMatrix extends AbstractMatrixTest {

	@Override
	public Matrix createMatrix(long... size) throws MatrixException {
		return new CommonsMathRealMatrix(size);
	}

	@Override
	public Matrix createMatrix(Matrix source) throws MatrixException {
		return new CommonsMathRealMatrix(source);
	}

}
