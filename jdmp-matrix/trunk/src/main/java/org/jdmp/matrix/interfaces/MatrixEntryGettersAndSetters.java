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

package org.jdmp.matrix.interfaces;

import org.jdmp.matrix.exceptions.MatrixException;

/**
 * This interface declares the getters and setters for the entries in the Matrix
 * for the most important Java types: boolean, int, char, float, double, long,
 * Date, Object and String.
 * <p>
 * The matrix will try to convert its content to the desired format as good as
 * possible. For example, a Matrix with String entries will try to parse each
 * String to a Double when you invoke getDoubleValue(). Note, that there will be
 * no exception if the entry cannot be converted. Instead, the result will be
 * either null, 0, false or Double.NaN depending on the method.
 */
public interface MatrixEntryGettersAndSetters {

	/**
	 * Returns a double representation of an entry in the matrix. The stored
	 * value will be converted to a double as good as possible.
	 * 
	 * @param coordinates
	 *            location of the entry
	 * @return a double representation of the entry
	 * @throws MatrixException
	 */
	public double getDouble(long... coordinates) throws MatrixException;

	/**
	 * Sets an entry in the matrix to a double value. If the matrix cannot store
	 * double values, the value will be represented as good as possible.
	 * 
	 * @param value
	 *            double value
	 * @param coordinates
	 *            location of the entry
	 * @throws MatrixException
	 */
	public void setDouble(double value, long... coordinates) throws MatrixException;

	/**
	 * Returns a raw entry in the matrix as it is stored. If the matrix supports
	 * Generics, the return type will match the type that is stored.
	 * 
	 * @param coordinates
	 *            location of the entry
	 * @return entry object
	 * @throws MatrixException
	 */
	public Object getObject(long... coordinates) throws MatrixException;

	/**
	 * Sets an entry in the matrix to an object. If the matrix cannot store this
	 * object type, the value will be represented as good as possible.
	 * 
	 * @param o
	 *            the object to store
	 * @param coordinates
	 *            location of the entry
	 * @throws MatrixException
	 */
	public void setObject(Object o, long... coordinates) throws MatrixException;

	/**
	 * Returns a String representation of an entry in the matrix. The stored
	 * value will be converted to a String as good as possible.
	 * 
	 * @param coordinates
	 *            location of the entry
	 * @return a String representation of the entry
	 * @throws MatrixException
	 */
	public String getString(long... coordinates) throws MatrixException;

	/**
	 * Sets an entry in the matrix to a String value. If the matrix cannot store
	 * Strings, the value will be represented as good as possible.
	 * 
	 * @param value
	 *            String value
	 * @param coordinates
	 *            location of the entry
	 * @throws MatrixException
	 */
	public void setString(String string, long... coordinates) throws MatrixException;

	/**
	 * Converts the content of a matrix into a 2-dimensional array of double
	 * values.
	 * 
	 * @return double array with matrix entries
	 * @throws MatrixException
	 */
	public double[][] toDoubleArray() throws MatrixException;

}
