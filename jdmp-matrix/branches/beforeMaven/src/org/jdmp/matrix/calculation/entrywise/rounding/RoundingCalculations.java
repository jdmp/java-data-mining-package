package org.jdmp.matrix.calculation.entrywise.rounding;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.Calculation.Ret;

public interface RoundingCalculations {

	/**
	 * Returns a matrix with all entries rounded to integer values.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @return Matrix with rounded values
	 */
	public Matrix round(Ret returnType) throws MatrixException;

	/**
	 * Returns a matrix with all entries rounded down to the next integer. The
	 * result is a new Matrix.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @return Matrix with rounded values
	 */
	public Matrix floor(Ret returnType) throws MatrixException;

	/**
	 * Returns a matrix with all entries rounded up to the next integer. The
	 * result is a new Matrix.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @return Matrix with rounded values
	 */
	public Matrix ceil(Ret returnType) throws MatrixException;

}
