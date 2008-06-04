package org.jdmp.jmatio;

import org.jdmp.matrix.AbstractMatrixTest;
import org.jdmp.matrix.Matrix;

public class TestMLDoubleMatrix extends AbstractMatrixTest {

	@Override
	public Matrix createMatrix(long... size) throws Exception {
		return new MLDoubleMatrix(size);
	}

	@Override
	public Matrix createMatrix(Matrix source) throws Exception {
		return new MLDoubleMatrix(source);
	}

}
