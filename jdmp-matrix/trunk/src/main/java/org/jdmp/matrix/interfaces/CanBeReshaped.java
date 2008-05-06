package org.jdmp.matrix.interfaces;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;

public interface CanBeReshaped {

	public Matrix reshape(long... newSize) throws MatrixException;

	public Matrix toColumnVector() throws MatrixException;

	public Matrix toRowVector() throws MatrixException;

}
