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

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.jdmp.core.util.DefaultObservableList;
import org.jdmp.core.util.MatrixListToMatrixWrapper;
import org.jdmp.core.util.ObservableList;
import org.ujmp.core.Coordinates;
import org.ujmp.core.Matrix;

public class DefaultVariable extends AbstractVariable {
	private static final long serialVersionUID = -7192491915167470355L;

	private transient Matrix matrixListMatrix = null;

	private long[] size = Coordinates.ZERO2D;

	private final ObservableList<Matrix> matrixList;

	public DefaultVariable() {
		super();
		matrixList = new DefaultObservableList<Matrix>(this);
		matrixList.addListDataListener(new VariableListDataListener());
	}

	public final long[] getInnerSize() {
		if (Coordinates.product(size) == 0) {
			Matrix m = getLast();
			if (m != null) {
				setInnerSize(Coordinates.copyOf(m.getSize()));
			}
		}
		return size;
	}

	public final void setInnerSize(long... size) {
		this.size = Coordinates.copyOf(size);
	}

	public Matrix getAsListMatrix() {
		if (matrixListMatrix == null) {
			matrixListMatrix = new MatrixListToMatrixWrapper(this);
		}
		return matrixListMatrix;
	}

	public ObservableList<Matrix> getMatrixList() {
		return matrixList;
	}

	public Variable clone() {
		Variable v = VariableFactory.labeledVariable(getLabel());
		for (Matrix m : getMatrixList()) {
			v.getMatrixList().add(m.clone());
		}
		return v;
	}

	class VariableListDataListener implements ListDataListener {

		public void contentsChanged(ListDataEvent e) {
			notifyGUIObject();
		}

		public void intervalAdded(ListDataEvent e) {
			notifyGUIObject();
		}

		public void intervalRemoved(ListDataEvent e) {
			notifyGUIObject();
		}

	}

}
