package org.jdmp.matrix.stubs;


public abstract class AbstractSparseMatrix<A> extends AbstractGenericMatrix<A> {

	public final boolean isSparse() {
		return true;
	}
}
