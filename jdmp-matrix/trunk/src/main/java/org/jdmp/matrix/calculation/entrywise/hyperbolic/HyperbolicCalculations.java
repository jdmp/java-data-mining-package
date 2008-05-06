package org.jdmp.matrix.calculation.entrywise.hyperbolic;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.Calculation.Ret;

public interface HyperbolicCalculations {

	// sinh, cosh, tanh, cotanh, sech, cosech
	// asinh, acosh, atanh, acotanh, asech, acosech

	/**
	 * Calculates the hyperbolic sinus of all entries in the matrix.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @return Matrix with hyperbolic sinus values
	 */
	public Matrix sinh(Ret returnType) throws MatrixException;

	/**
	 * Calculates the hyperbolic cosinus of all entries in the matrix.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @return Matrix with hyperbolic cosinus values
	 */
	public Matrix cosh(Ret returnType) throws MatrixException;

	/**
	 * Calculates the hyperbolic tangens of all entries in the matrix.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @return Matrix with hyperbolic tangens values
	 */
	public Matrix tanh(Ret returnType) throws MatrixException;

}
