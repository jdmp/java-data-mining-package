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

package org.jdmp.core.variable;

import javax.swing.event.EventListenerList;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.collections.DefaultMatrixList;
import org.ujmp.core.collections.MatrixList;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.util.MatrixListToMatrixWrapper;

public class DefaultVariable extends AbstractVariable {
	private static final long serialVersionUID = -7192491915167470355L;

	private transient Matrix matrixListMatrix = null;

	private long[] size = Coordinates.ZERO2D;

	private MatrixList matrixList = null;

	private String label = "";

	private String description = "";

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
		matrixList = new DefaultMatrixList();
	}

	public DefaultVariable(int count) {
		this();
		matrixList = new DefaultMatrixList(count);
	}

	public DefaultVariable(MatrixList matrixList) {
		this();
		this.matrixList = matrixList;
		setSize(1, matrixList.getTraceCount());
	}

	public DefaultVariable(Matrix matrix) {
		this();
		matrixList = new DefaultMatrixList(1);
		addMatrix(matrix);
	}

	public DefaultVariable(String label, Matrix matrix) {
		this(matrix);
		setLabel(label);
	}

	public DefaultVariable(String label) {
		this();
		setLabel(label);
	}

	public DefaultVariable(String label, double value) {
		this();
		setLabel(label);
		addMatrix(MatrixFactory.linkToValue(value));
	}

	public DefaultVariable(String label, int memorySize) {
		this();
		setLabel(label);
		this.matrixList = new DefaultMatrixList(memorySize);
	}

	public DefaultVariable(String label, int rows, int columns) {
		this();
		setLabel(label);
		setSize(rows, columns);
		this.matrixList = new DefaultMatrixList((int) Math.ceil(1000000.0 / rows / columns));
	}

	public DefaultVariable(String label, int memorySize, int rows, int columns) {
		this();
		setSize(rows, columns);
		this.matrixList = new DefaultMatrixList(memorySize);
		setLabel(label);
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

	public MatrixList getMatrixList() {
		return matrixList;
	}

}
