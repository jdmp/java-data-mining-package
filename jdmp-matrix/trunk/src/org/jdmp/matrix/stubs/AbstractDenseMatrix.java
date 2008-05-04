package org.jdmp.matrix.stubs;

import org.jdmp.matrix.AbstractGenericMatrix;

public abstract class AbstractDenseMatrix<A> extends AbstractGenericMatrix<A> {

	public final boolean isSparse() {
		return false;
	}

}
