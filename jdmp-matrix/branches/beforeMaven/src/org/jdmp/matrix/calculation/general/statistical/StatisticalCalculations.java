package org.jdmp.matrix.calculation.general.statistical;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.Calculation.Ret;

public interface StatisticalCalculations {

	/**
	 * Calculates the mimimum of the values in the matrix either rowwise,
	 * columnwise, or global.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param dimension
	 *            the axis along which should be calculated, e.g. ROW=0,
	 *            COLUMN=1 or ALL
	 * @return A new matrix containing the minimum values
	 */
	public Matrix min(Ret returnType, int dimension) throws MatrixException;

	/**
	 * Calculates the maximum of the values in the matrix either rowwise,
	 * columnwise, or global.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param dimension
	 *            the axis along which should be calculated, e.g. ROW=0,
	 *            COLUMN=1 or ALL
	 * @return A new matrix containing the maximum values
	 */
	public Matrix max(Ret returnType, int dimension) throws MatrixException;

	/**
	 * Calculates the sum of all entries in the Matrix either per row, per
	 * column, or global.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param dimension
	 *            The axis along which to calculate
	 * @param ignoreNaN
	 *            should missing values be ignored
	 * @return Matrix with the sum of the values along the desired axis
	 */
	public Matrix sum(Ret returnType, int dimension, boolean ignoreNaN) throws MatrixException;

	/**
	 * Calculates the mean of all entries in the Matrix either per row, per
	 * column, or global.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param dimension
	 *            The axis along which to calculate
	 * @param ignoreNaN
	 *            should missing values be ignored
	 * @return Matrix with the sum of the values along the desired axis
	 */
	public Matrix mean(Ret returnType, int dimension, boolean ignoreNaN) throws MatrixException;

	/**
	 * Calculates the variance of all entries in the Matrix either per row, per
	 * column, or global.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param dimension
	 *            The axis along which to calculate
	 * @param ignoreNaN
	 *            should missing values be ignored
	 * @return Matrix with the sum of the values along the desired axis
	 */
	public Matrix var(Ret returnType, int dimension, boolean ignoreNaN) throws MatrixException;

	/**
	 * Calculates the standard deviation of all entries in the Matrix either per
	 * row, per column, or global.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param dimension
	 *            The axis along which to calculate
	 * @param ignoreNaN
	 *            should missing values be ignored
	 * @return Matrix with the sum of the values along the desired axis
	 */
	public Matrix std(Ret returnType, int dimension, boolean ignoreNaN) throws MatrixException;

	/**
	 * Calculates the covariance Matrix. Each row is an observation and each
	 * column is a variable.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param ignoreNaN
	 *            should missing values be ignored
	 * @return Covariance Matrix
	 */
	public Matrix cov(Ret returnType, boolean ignoreNaN) throws MatrixException;

	/**
	 * Calculates the Pearson correlation. Each row is an observation and each
	 * column is a variable.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param ignoreNaN
	 *            should missing values be ignored
	 * @return Covariance Matrix
	 */
	public Matrix corrcoef(Ret returnType, boolean ignoreNaN) throws MatrixException;

	/**
	 * Finds the index of the maximum value in the matrix
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param dimension
	 *            The axis along which to calculate
	 * @return Matrix containing the coordinates of the Maximum
	 */
	public Matrix indexOfMax(Ret returnType, int dimension) throws MatrixException;

	/**
	 * Finds the index of the minimum value in the matrix
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param dimension
	 *            The axis along which to calculate
	 * @return Matrix containing the coordinates of the Minimum
	 */
	public Matrix indexOfMin(Ret returnType, int dimension) throws MatrixException;

}
