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

	public Matrix addColumnWithOnes() throws MatrixException;

	public Matrix addRowWithOnes() throws MatrixException;

	public Matrix rescaleEntries() throws MatrixException;

	public void rescaleEntries_() throws MatrixException;

	public Matrix rescaleEntries(int axis, double targetMin, double targetMax) throws MatrixException;

	public void rescaleEntries_(int axis, double targetMin, double targetMax) throws MatrixException;

	public void addNoise_(double noiselevel) throws MatrixException;

	public Matrix replaceMissingBy(Matrix matrix) throws MatrixException;

	public void fadeIn_(int axis, long start, long end) throws MatrixException;

	public void fadeOut_(int axis, long start, long end) throws MatrixException;

	public void fadeIn_() throws MatrixException;

	public void fadeOut_() throws MatrixException;
	
	public  Matrix convertIntToVector(int numberOfClasses) throws MatrixException;
	
	public  void greaterOrZero_() throws MatrixException ;
	
	public  void scaleRowsToOne_() throws MatrixException;
	
	public  Matrix appendHorizontally(Matrix m) throws MatrixException ;
	
	public  Matrix appendVertically(Matrix m) throws MatrixException ;
	
	public  Matrix append(int dimension, Matrix m) throws MatrixException ;

}
