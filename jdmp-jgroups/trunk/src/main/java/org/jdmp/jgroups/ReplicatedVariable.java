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

package org.jdmp.jgroups;

import javax.swing.event.EventListenerList;

import org.jdmp.core.variable.AbstractVariable;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.collections.MatrixList;
import org.ujmp.core.coordinates.Coordinates;

public class ReplicatedVariable extends AbstractVariable {
	private static final long serialVersionUID = -2486416251545919644L;

	private static int runningId = 0;

	private transient final Matrix matrixListMatrix = null;

	private MatrixList matrixList = null;

	private transient EventListenerList listenerList = null;

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

	public final EventListenerList getListenerList() {
		if (listenerList == null) {
			listenerList = new EventListenerList();
		}
		return listenerList;
	}

	private long[] size = Coordinates.ZERO2D;

	public ReplicatedVariable() {
		this("Variable" + runningId++, 100, 0, 0);
	}

	public ReplicatedVariable(String label) {
		this(label, 100, 0, 0);
	}

	public ReplicatedVariable(int count) {
		this("Variable" + runningId++, count, 0, 0);
	}

	public ReplicatedVariable(Matrix matrix) {
		this("Variable" + runningId++, 1, matrix.getRowCount(), matrix.getColumnCount());
		addMatrix(matrix);
	}

	public ReplicatedVariable(String label, Matrix matrix) {
		this(label, 1, matrix.getRowCount(), matrix.getColumnCount());
		addMatrix(matrix);
	}

	public ReplicatedVariable(String label, int count) {
		this(label, count, 0, 0);
	}

	public ReplicatedVariable(String label, double value) {
		this(label, 1, 1, 1);
		addMatrix(MatrixFactory.linkToValue(value));
	}

	public ReplicatedVariable(String label, long rows, long columns) {
		this(label, (int) Math.ceil(1000000.0 / rows / columns), rows, columns);
	}

	public ReplicatedVariable(String label, int memorySize, long rows, long columns) {
		super();
		this.matrixList = new ReplicatedMatrixList(this, label, memorySize);
		setLabel(label);
		setSize(rows, columns);
	}

	public MatrixList getMatrixList() {
		return matrixList;
	}

	public void setMemorySize(int size) {
		matrixList.setMaxCount(size);
	}

	public final long[] getSize() {
		return size;
	}

	public final void setSize(long... size) {
		this.size = Coordinates.copyOf(size);
	}

	public Matrix getAsMatrix() {
		return null;
	}

}
