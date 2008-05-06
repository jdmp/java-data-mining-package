package org.jdmp.matrix.calculation.entrywise.replace;

import java.util.regex.Pattern;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.Calculation.Ret;

public interface ReplaceCalculations {

	/**
	 * Replaces matching text in every entry of the matrix.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param search
	 *            Regular expression to search for
	 * @param replacement
	 *            Replacement String
	 * @return matrix with modified entries
	 */
	public Matrix replaceRegex(Ret returnType, String search, String replacement) throws MatrixException;

	/**
	 * Replaces matching text in every entry of the matrix.
	 * 
	 * @param returnType
	 *            Select whether a new or a linked Matrix is returned, or if the
	 *            operation is performed on the original Matrix
	 * @param search
	 *            Regular expression pattern to search for
	 * @param replacement
	 *            Replacement String
	 * @return matrix with modified entries
	 */
	public Matrix replaceRegex(Ret returnType, Pattern search, String replacement) throws MatrixException;

}
