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

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.interfaces.HasSourceMatrix;
import org.jdmp.matrix.stubs.AbstractGenericMatrix;
import org.jdmp.matrix.util.collections.DefaultMatrixList;
import org.jdmp.matrix.util.collections.MatrixList;

public class SynchronizedMatrix<A> extends AbstractGenericMatrix<A> implements HasSourceMatrix {
	private static final long serialVersionUID = -4456493053286654056L;

	private Matrix matrix = null;

	public SynchronizedMatrix(Matrix source) {
		this.matrix = source;
		setAnnotation(source.getAnnotation());
	}

	public synchronized Iterable<long[]> allCoordinates() {
		return matrix.allCoordinates();
	}

	public synchronized long[] getSize() {
		return matrix.getSize();
	}

	public synchronized double getDouble(long... coordinates) throws MatrixException {
		return matrix.getDouble(coordinates);
	}

	@Override
	public synchronized long getValueCount() {
		return matrix.getValueCount();
	}

	public synchronized boolean isSparse() {
		return matrix.isSparse();
	}

	public synchronized void setDouble(double value, long... coordinates) throws MatrixException {
		matrix.setDouble(value, coordinates);
	}


	public synchronized A getObject(long... c) throws MatrixException {
		return (A) matrix.getObject(c);
	}

	public synchronized void setObject(Object value, long... c) throws MatrixException {
		matrix.setObject(value, c);
	}

	public synchronized boolean contains(long... coordinates) {
		return matrix.contains(coordinates);
	}

	@Override
	public synchronized boolean isReadOnly() {
		return matrix.isReadOnly();
	}

	public Matrix getSourceMatrix() {
		return matrix;
	}

	public MatrixList getSourceMatrices() {
		MatrixList matrices = new DefaultMatrixList();
		if (getSourceMatrix() instanceof HasSourceMatrix) {
			matrices.addAll(((HasSourceMatrix) getSourceMatrix()).getSourceMatrices());
		}
		matrices.add(getSourceMatrix());
		return matrices;
	}

	public EntryType getEntryType() {
		return matrix.getEntryType();
	}

}
