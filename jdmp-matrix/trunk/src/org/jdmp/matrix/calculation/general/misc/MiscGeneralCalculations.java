package org.jdmp.matrix.calculation.general.misc;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.Calculation.Ret;

public interface MiscGeneralCalculations {

	/**
	 * Subtracts the mean from the matrix.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param dimension
	 *            The axis along which to calculate
	 * @param ignoreNaN
	 *            should missing values be ignored
	 * @return Matrix with zero mean.
	 */
	public Matrix center(Ret returnType, int dimension, boolean ignoreNaN) throws MatrixException;

	/**
	 * Subtracts the mean from the matrix and divides by the standard deviation.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param dimension
	 *            The axis along which to calculate
	 * @param ignoreNaN
	 *            should missing values be ignored
	 * @return Matrix with zero mean and unit variance.
	 */
	public Matrix standardize(Ret returnType, int dimension, boolean ignoreNaN) throws MatrixException;

}
