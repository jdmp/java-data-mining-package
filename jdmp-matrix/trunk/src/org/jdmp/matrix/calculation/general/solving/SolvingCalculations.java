package org.jdmp.matrix.calculation.general.solving;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;

public interface SolvingCalculations {

	/**
	 * Calculates the inverse of the Matrix using either LUDecomposition (for
	 * square matrices) or QRDecomposition (otherwise).
	 * 
	 * @return Inverse of the matrix
	 */
	public Matrix inv() throws MatrixException;

	/**
	 * Calculates the pseudo inverse of the Matrix using Singular Value
	 * Decomposition.
	 * 
	 * @return Pseudo inverse of the Matrix
	 */
	public Matrix pinv() throws MatrixException;

	/**
	 * Projects the matrix into the space of the principal components.
	 * 
	 * @return Matrix projected on principal components.
	 */
	public Matrix princomp() throws MatrixException;

}
