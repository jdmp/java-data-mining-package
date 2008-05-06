package org.jdmp.matrix.calculation.entrywise.trigonometric;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.Calculation.Ret;

/**
 * This interface lists all trigonometric functions that can be calculated on a
 * Matrix.
 */
public interface TrigonometricCalculations {

	// sin, cos, tan, cot, sec, cosec
	// asin, acos, atan, acot, asec, acosec

	/**
	 * Calculates the sinus of all entries in the matrix.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @return Matrix with sinus values
	 */
	public Matrix sin(Ret returnType) throws MatrixException;

	/**
	 * Calculates the cosinus of all entries in the matrix.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @return Matrix with cosinus values
	 */
	public Matrix cos(Ret returnType) throws MatrixException;

	/**
	 * Calculates the tangens of all entries in the matrix.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @return Matrix with tangens values
	 */
	public Matrix tan(Ret returnType) throws MatrixException;

}
