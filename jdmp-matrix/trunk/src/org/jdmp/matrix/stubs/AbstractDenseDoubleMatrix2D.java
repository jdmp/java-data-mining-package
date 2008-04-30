package org.jdmp.matrix.stubs;

import org.jdmp.matrix.CoordinateIterator2D;

public abstract class AbstractDenseDoubleMatrix2D extends AbstractDenseDoubleMatrix {

	public final Iterable<long[]> allCoordinates() {
		return new CoordinateIterator2D(getSize());
	}

}
