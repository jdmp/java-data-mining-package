/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
 *
 * This file is part of the Java Data Mining Package (JDMP).
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership and licensing.
 *
 * JDMP is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * JDMP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with JDMP; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.jdmp.matrix.io;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.implementations.basic.DefaultDenseStringMatrix2D;

public abstract class ImportMatrixM {

	/**
	 * Creates a DefaultDenseStringMatrix2D from a given String. The string
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
	public static Matrix fromString(String string,Object ... parameters) {
		return new DefaultDenseStringMatrix2D(string);
	}

}
