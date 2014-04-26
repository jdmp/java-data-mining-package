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

package org.jdmp.core.variable;

import org.jdmp.core.util.ObservableList;
import org.jdmp.core.util.SingletonObservableList;
import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;

public class SingletonVariable extends AbstractVariable {
	private static final long serialVersionUID = -6210738091770692066L;

	private ObservableList<Matrix> matrixList = null;

	public SingletonVariable(Matrix matrix) {
		this.matrixList = new SingletonObservableList<Matrix>(matrix);
	}

	public Variable clone() {
		Variable v = new SingletonVariable(getLatestMatrix());
		return v;
	}

	public synchronized Matrix getAsListMatrix() {
		return getLatestMatrix();
	}

	public synchronized long[] getInnerSize() {
		Matrix m = getLatestMatrix();
		return m == null ? Coordinates.ZERO2D : m.getSize();
	}

	public void setInnerSize(long... size) {
	}

	public ObservableList<Matrix> getMatrixList() {
		return matrixList;
	}
}
