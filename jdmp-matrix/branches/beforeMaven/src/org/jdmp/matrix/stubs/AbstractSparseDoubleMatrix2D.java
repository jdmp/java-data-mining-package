package org.jdmp.matrix.stubs;

import org.jdmp.matrix.CoordinateIterator2D;

public abstract class AbstractSparseDoubleMatrix2D extends AbstractSparseDoubleMatrix {

	public final Iterable<long[]> allCoordinates() {
		return new CoordinateIterator2D(getSize());
	}

}
