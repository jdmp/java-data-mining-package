/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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

import java.util.LinkedList;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.jdmp.core.util.DefaultObservableList;
import org.jdmp.core.util.MatrixListToMatrixWrapper;
import org.jdmp.core.util.ObservableList;
import org.ujmp.core.Matrix;
import org.ujmp.core.collections.RingBufferList;
import org.ujmp.core.coordinates.Coordinates;

public class DefaultVariable extends AbstractVariable {
	private static final long serialVersionUID = -7192491915167470355L;

	private transient Matrix matrixListMatrix = null;

	private long[] size = Coordinates.ZERO2D;

	private ObservableList<Matrix> matrixList = null;

	private String label = null;

	private String description = null;

	public final String getDescription() {
		return description;
	}

	public final void setDescription(String description) {
		this.description = description;
	}

	public String getLabel() {
		return label;
	}

	public final void setLabel(String label) {
		this.label = label;
	}

	public DefaultVariable() {
		super();
		matrixList = new DefaultObservableList<Matrix>(new LinkedList<Matrix>());
		matrixList.addListDataListener(new VariableListDataListener());
	}

	public DefaultVariable(int memorySize) {
		super();
		matrixList = new DefaultObservableList<Matrix>(new RingBufferList<Matrix>(memorySize));
		matrixList.addListDataListener(new VariableListDataListener());
	}

	public final long[] getSize() {
		if (Coordinates.product(size) == 0) {
			Matrix m = getMatrix();
			if (m != null) {
				setSize(Coordinates.copyOf(m.getSize()));
			}
		}
		return size;
	}

	public final void setSize(long... size) {
		this.size = Coordinates.copyOf(size);
	}

	public Matrix getAsMatrix() {
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
			v.getMatrixList().add(m.copy());
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
