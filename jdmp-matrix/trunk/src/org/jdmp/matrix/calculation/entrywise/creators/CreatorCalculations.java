package org.jdmp.matrix.calculation.entrywise.creators;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;

public interface CreatorCalculations {

	/**
	 * Returns a matrix with equal size, where all entries are set to zero.
	 * 
	 * @return Matrix with zeros.
	 */
	public Matrix zeros() throws MatrixException;

	/**
	 * Returns a matrix with equal size, where all entries are set to 1.0.
	 * 
	 * @return Matrix with ones.
	 */
	public Matrix ones() throws MatrixException;

	/**
	 * Returns a matrix with equal size, where all entries are set to uniform
	 * random values between 0.0 and 1.0.
	 * 
	 * @return Matrix with uniformly distributed values.
	 */
	public Matrix rand() throws MatrixException;

	/**
	 * Returns a matrix with equal size, where all entries are set to random
	 * values which are normally distributed with 0.0 mean and 1.0 standard
	 * deviation.
	 * 
	 * @return Matrix with normally distributed values.
	 */
	public Matrix randn() throws MatrixException;

	/**
	 * Returns a matrix with equal size, where all entries are set to a desired
	 * value.
	 * 
	 * @param value
	 *            fill with this value
	 * @return Matrix with ones.
	 */
	public Matrix fill(double value) throws MatrixException;

	/**
	 * Returns a matrix with ones at the diagonal.
	 * 
	 * @return Eye matrix with ones at the diagonal
	 */
	public Matrix eye() throws MatrixException;

}
