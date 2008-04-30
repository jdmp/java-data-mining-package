package org.jdmp.matrix.stubs;

public abstract class AbstractSparseObjectMatrix<A> extends AbstractObjectMatrix<A> {

	public final boolean isSparse() {
		return true;
	}

}
