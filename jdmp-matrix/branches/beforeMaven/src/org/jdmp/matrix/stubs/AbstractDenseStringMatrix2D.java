package org.jdmp.matrix.stubs;

import org.jdmp.matrix.CoordinateIterator2D;

public abstract class AbstractDenseStringMatrix2D extends AbstractDenseStringMatrix {

	public Iterable<long[]> allCoordinates() {
		return new CoordinateIterator2D(getSize());
	}

}
