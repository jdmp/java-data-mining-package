package org.jdmp.matrix.interfaces;

import org.jdmp.matrix.Matrix;

public interface CanBeReshaped {

	public Matrix reshape(long... newSize);

}
