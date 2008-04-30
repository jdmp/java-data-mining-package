package org.jdmp.matrix.stubs;

import org.jdmp.matrix.CoordinateIterator2D;

public abstract class AbstractDenseObjectMatrix2D<A> extends AbstractDenseObjectMatrix<A> {

	public Iterable<long[]> allCoordinates() {
		return new CoordinateIterator2D(getSize());
	}

}
