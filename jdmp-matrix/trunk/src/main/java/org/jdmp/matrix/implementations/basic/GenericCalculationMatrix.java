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

package org.jdmp.matrix.implementations.basic;

import org.jdmp.matrix.calculation.AbstractGenericCalculation;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.stubs.AbstractGenericMatrix;

public class GenericCalculationMatrix<A> extends AbstractGenericMatrix<A> {
	private static final long serialVersionUID = -8345796002435936888L;

	private AbstractGenericCalculation<A> calculation = null;

	public GenericCalculationMatrix(AbstractGenericCalculation<A> calculation) {
		this.calculation = calculation;
		setAnnotation(calculation.getAnnotation());
	}

	public EntryType getEntryType() {
		return calculation.getEntryType();
	}

	public boolean contains(long... coordinates) {
		return calculation.contains(coordinates);
	}

	public Iterable<long[]> allCoordinates() {
		return calculation.allCoordinates();
	}

	public Iterable<long[]> availableCoordinates() {
		return calculation.availableCoordinates();
	}

	public long[] getSize() {
		return calculation.getSize();
	}

	public double getDouble(long... coordinates) throws MatrixException {
		return calculation.getDouble(coordinates);
	}

	public void setDouble(double value, long... coordinates) throws MatrixException {
		calculation.setDouble(value, coordinates);
	}

	@Override
	public A getObject(long... coordinates) throws MatrixException {
		return (A) calculation.getObject(coordinates);
	}

	public void setObject(Object o, long... coordinates) throws MatrixException {
		calculation.setObject(o, coordinates);
	}

	@Override
	public String getString(long... coordinates) throws MatrixException {
		return calculation.getString(coordinates);
	}

	public void setString(String s, long... coordinates) throws MatrixException {
		calculation.setString(s, coordinates);
	}

	public boolean isSparse() {
		return calculation.isSparse();
	}

}
