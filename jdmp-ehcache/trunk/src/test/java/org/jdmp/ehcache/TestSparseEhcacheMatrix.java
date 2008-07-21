package org.jdmp.ehcache;

import org.ujmp.core.AbstractMatrixTest;
import org.ujmp.core.Matrix;

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
