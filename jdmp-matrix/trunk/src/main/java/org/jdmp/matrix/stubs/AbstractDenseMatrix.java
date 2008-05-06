package org.jdmp.matrix.stubs;


public abstract class AbstractDenseMatrix<A> extends AbstractGenericMatrix<A> {

	public final boolean isSparse() {
		return false;
	}

}
