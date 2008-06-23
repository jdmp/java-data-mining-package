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

package org.jdmp.matrix;

import org.jdmp.matrix.exceptions.MatrixException;

public interface ByteMatrix extends Matrix {

	/**
	 * Returns a byte representation of an entry in the matrix. The stored value
	 * will be converted to a byte as good as possible.
	 * 
	 * @param coordinates
	 *            location of the entry
	 * @return a byte representation of the entry
	 * @throws MatrixException
	 */
	public byte getAsByte(long... coordinates) throws MatrixException;

	/**
	 * Sets an entry in the matrix to a byte value. If the matrix cannot store
	 * byte values, the value will be represented as good as possible.
	 * 
	 * @param value
	 *            byte value
	 * @param coordinates
	 *            location of the entry
	 * @throws MatrixException
	 */
	public void setAsByte(byte value, long... coordinates) throws MatrixException;

	/**
	 * Converts the content of a matrix into a 2-dimensional array of byte
	 * values.
	 * 
	 * @return byte array with matrix entries
	 * @throws MatrixException
	 */
	public byte[][] toByteArray() throws MatrixException;

}
