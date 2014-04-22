/*
 * Copyright (C) 2008-2014 by Holger Arndt
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

package org.jdmp.mallet.matrix;

import org.ujmp.core.doublematrix.SparseDoubleMatrix2D;
import org.ujmp.core.doublematrix.factory.SparseDoubleMatrix2DFactory;
import org.ujmp.core.doublematrix.stub.AbstractSparseDoubleMatrix2D;

import cc.mallet.types.Labeling;

public class Labeling2Matrix extends AbstractSparseDoubleMatrix2D {
	private static final long serialVersionUID = 5834883719134722053L;

	private Labeling labeling = null;

	public Labeling2Matrix(Labeling labeling) {
		super(1, labeling.getLabelAlphabet().size());
		this.labeling = labeling;
	}

	public double getDouble(long row, long column) {
		return labeling.valueAtLocation((int) column);
	}

	public double getDouble(int row, int column) {
		return labeling.valueAtLocation(column);
	}

	public void setDouble(double value, long row, long column) {
		throw new RuntimeException("not allowed");
	}

	public void setDouble(double value, int row, int column) {
		throw new RuntimeException("not allowed");
	}

	public boolean contains(long... coordinates) {
		return labeling.valueAtLocation((int) coordinates[COLUMN]) != 0;
	}

	public long[] getSize() {
		return new long[] { 1, labeling.getLabelAlphabet().size() };
	}

	public SparseDoubleMatrix2DFactory<? extends SparseDoubleMatrix2D> getFactory() {
		// TODO Auto-generated method stub
		return null;
	}

}
