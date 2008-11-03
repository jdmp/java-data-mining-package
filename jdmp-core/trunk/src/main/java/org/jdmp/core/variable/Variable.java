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

import org.jdmp.core.CoreObject;
import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.HasDescription;
import org.ujmp.core.interfaces.HasLabel;
import org.ujmp.core.interfaces.HasMatrix;
import org.ujmp.core.interfaces.HasMatrixList;

public interface Variable extends CoreObject, HasLabel, HasDescription, HasMatrix, HasMatrixList {
	public static final Class<?>[] VARIABLEARRAY = new Class<?>[] { new Variable[] {}.getClass() };

	public long[] getSize();

	public void setSize(long... size);

	public int getMemorySize();

	public double getValue() throws MatrixException;

	public void setValue(double value);

	public Matrix getAsMatrix();

	public void addMatrix(Matrix m);

	public void removeVariableListener(VariableListener l);

	public void fireVariableEvent(VariableEvent e);

	public void setMatrix(int index, Matrix m);

	public void addVariableListener(VariableListener l);

	public int getIndexOfMatrix(Matrix m);

	public long getRowCount();

	public long getColumnCount();

	public double getMinValue() throws MatrixException;

	public double getMaxValue() throws MatrixException;

	public long getIndexOfMaximum() throws MatrixException;

	public long getIndexOfMinimum() throws MatrixException;

	public Matrix getMeanMatrix() throws MatrixException;

	public Matrix getMaxMatrix() throws MatrixException;

	public Matrix getMinMatrix() throws MatrixException;

	public Matrix getVarianceMatrix() throws MatrixException;

	public Matrix getStandardDeviationMatrix() throws MatrixException;
}
