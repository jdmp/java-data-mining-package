package org.jdmp.matrix.calculation;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.Calculation.Calc;
import org.jdmp.matrix.calculation.Calculation.Ret;
import org.jdmp.matrix.calculation.basic.BasicCalculations;
import org.jdmp.matrix.calculation.entrywise.EntrywiseCalculations;
import org.jdmp.matrix.calculation.general.GeneralCalculations;

/**
 * This interface declares all possibilities to perform calculations on a
 * matrix. For standard functions you can choose from Calculcation.Calc or
 * provide a String to select the desired function, e.g. "sin", "sum", "mean",
 * "cov", etc.
 * <p>
 * If you want to implement your own calculation, you should implement the
 * interface Calculation and use the the method Matrix.calc(myCalculation) with
 * your implementation.
 * 
 * @see Calculation.Calc for available calculations
 * @author Holger Arndt
 * 
 */
public interface CanPerformCalculations extends BasicCalculations, EntrywiseCalculations, GeneralCalculations {

	public Matrix calcNew(Calculation calculation) throws MatrixException;

	public Matrix calc(Calculation calculation, Ret returnType) throws MatrixException;

	public Matrix calcNew(String calculation, Matrix... matrices) throws MatrixException;

	public Matrix calcNew(String calculation, int dimension, Matrix... matrices) throws MatrixException;

	public Matrix calc(String calculation, Ret returnType, Matrix... matrices) throws MatrixException;

	public Matrix calc(String calculation, Ret returnType, int dimension, Matrix... matrices) throws MatrixException;

	public Matrix calcNew(Calc calculation, Matrix... matrices) throws MatrixException;

	public Matrix calcNew(Calc calculation, int dimension, Matrix... matrices) throws MatrixException;

	public Matrix calc(Calc calculation, Ret returnType, Matrix... matrices) throws MatrixException;

	public Matrix calc(Calc calculation, Ret returnType, int dimension, Matrix... matrices) throws MatrixException;

}
