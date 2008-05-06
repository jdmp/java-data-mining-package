package org.jdmp.matrix.stubs;

import org.jdmp.matrix.Coordinates;

public abstract class AbstractDenseDoubleMatrix extends AbstractDoubleMatrix {

	public final boolean contains(long... coordinates) {
		return Coordinates.isSmallerThan(coordinates, getSize());
	}
	
	public final boolean isSparse() {
		return false;
	}

}
