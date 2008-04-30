package org.jdmp.matrix.io;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.implementations.basic.DefaultDenseStringMatrix2D;

public abstract class ImportM {

	/**
	 * Creates a DefaultFullStringMatrix2D from a given String. The string
	 * contains the rows of the matrix separated by semicolons or new lines. The
	 * columns of the matrix are separated by spaces or commas. All types of
	 * brackets are ignored.
	 * <p>
	 * Examples: "[1 2 3; 4 5 6]" or "(test1,test2);(test3,test4)"
	 * 
	 * @param string
	 *            the string to parse
	 * @return a StringMatrix with the desired values
	 */
	public static Matrix fromString(String string) {
		return new DefaultDenseStringMatrix2D(string);
	}

}
