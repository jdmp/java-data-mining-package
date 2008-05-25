package org.jdmp.ehcache;

import org.jdmp.matrix.AbstractMatrixTest;
import org.jdmp.matrix.Matrix;

public class TestSparseEhcacheMatrix extends AbstractMatrixTest {

	@Override
	public Matrix createMatrix(long... size) throws Exception {
		return new SparseEhcacheMatrix<String>(size);
	}

	@Override
	public Matrix createMatrix(Matrix source) throws Exception {
		return new SparseEhcacheMatrix<String>(source);
	}

}
