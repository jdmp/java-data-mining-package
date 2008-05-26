package org.jdmp.matrix.implementations;

import org.jdmp.matrix.AbstractMatrixTest;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.implementations.basic.DefaultTiledObjectMatrix2D;

public class TestDefaultTiledObjectMatrix2D extends AbstractMatrixTest {

	@Override
	public Matrix createMatrix(long... size) throws Exception {
		return new DefaultTiledObjectMatrix2D<Object>(size);
	}

	@Override
	public Matrix createMatrix(Matrix source) throws Exception {
		return new DefaultTiledObjectMatrix2D<Object>(source);
	}

}
