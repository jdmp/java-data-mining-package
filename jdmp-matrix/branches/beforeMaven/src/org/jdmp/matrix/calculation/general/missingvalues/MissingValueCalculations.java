package org.jdmp.matrix.calculation.general.missingvalues;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.Calculation.Ret;

public interface MissingValueCalculations {

	/**
	 * Adds a specified amount of missing values (Double.NaN) to the Matrix
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param dimension
	 *            The axis along which to calculate
	 * @param percentMissing
	 *            defines how many values are missing 0.0 to 1.0
	 * @return Matrix with missing values
	 * @throws MatrixException
	 */
	public Matrix addMissing(Ret returnType, int dimension, double... percentMissing) throws MatrixException;

	/**
	 * Counts the missing values within the matrix, i.e. Infinity or NaN
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param dimension
	 *            The axis along which to calculate
	 * @return Matrix with counts of missing values
	 * @throws MatrixException
	 */
	public Matrix countMissing(Ret returnType, int dimension) throws MatrixException;

	/**
	 * Replaces missing values by row mean or column mean
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param dimension
	 *            The axis along which to calculate
	 * @return Matrix with missing values replaced
	 * @throws MatrixException
	 */
	public Matrix imputeMean(Ret returnType, int dimension) throws MatrixException;

	/**
	 * Replaces missing values by zero
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @return Matrix with missing values replaced
	 * @throws MatrixException
	 */
	public Matrix imputeZero(Ret returnType) throws MatrixException;

	/**
	 * Replaces missing values by the K nearest neighbors
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param dimension
	 *            The axis along which to calculate nearest neighbors
	 * @return Matrix with missing values replaced
	 * @throws MatrixException
	 */
	public Matrix imputeKNN(Ret returnType, int dimension) throws MatrixException;

	/**
	 * Replaces missing values by regression on other variables
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @return Matrix with missing values replaced
	 * @throws MatrixException
	 */
	public Matrix imputeRegression(Ret returnType) throws MatrixException;

	/**
	 * Replaces missing values by regression on other variables
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param firstGuess
	 *            initial guess to replace missing values, e.g. by mean
	 * @return Matrix with missing values replaced
	 * @throws MatrixException
	 */
	public Matrix imputeRegression(Ret returnType, Matrix firstGuess) throws MatrixException;

	/**
	 * Replaces missing values by regression on other variables. Missing values
	 * are initially replaced by mean. After that they are re-estimated several
	 * times until the matrix does not change anymore.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param dimension
	 *            The axis along which to calculate
	 * @return Matrix with missing values replaced
	 * @throws MatrixException
	 */
	public Matrix imputeEM(Ret returnType, int dimension) throws MatrixException;

	public Matrix deleteColumnsWithMissingValues(Ret returnType) throws MatrixException;

	public Matrix deleteRowsWithMissingValues(Ret returnType) throws MatrixException;

}
