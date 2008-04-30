package org.jdmp.matrix.stubs;

import org.jdmp.matrix.Coordinates;

public abstract class AbstractDenseObjectMatrix<A> extends AbstractObjectMatrix<A> {

	public final boolean contains(long... coordinates) {
		return Coordinates.isSmallerThan(coordinates, getSize());
	}

	public final boolean isSparse() {
		return false;
	}

}
