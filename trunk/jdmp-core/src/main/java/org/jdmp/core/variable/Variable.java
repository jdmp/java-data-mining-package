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

import org.jdmp.core.JDMPCoreObject;
import org.jdmp.core.matrix.HasMatrixList;
import org.ujmp.core.Matrix;

public interface Variable extends JDMPCoreObject, HasMatrixList {
	public static final Class<?>[] VARIABLEARRAY = new Class<?>[] { new Variable[] {}.getClass() };

	public static final String TAGS = "Tags";

	public static final String TOTAL = "Total";

	public static final String URL = "URL";

	public static final String LABEL = "Label";

	public static final String DESCRIPTION = "Description";

	public static final String CONTENT = "Content";

	public static final String LINKS = "Links";

	public static final String TYPE = "Type";

	public static final String SOURCE = "Source";

	public static final String DIMENSION = "Dimension";

	public static final String IGNORENAN = "IgnoreNaN";

	public static final String TARGET = "Target";

	public static final String INPUT = "Input";

	public static final String ID = "Id";

	public static final String DIFFERENCE = "Difference";

	public static final String RMSE = "RMSE";

	public static final String PREDICTED = "Predicted";

	public static final String WEIGHT = "Weight";

	public static final String SCORE = "Score";

	public static final String PROBABILITY = "Probability";

	public static final String COUNT = "Count";

	public static final String SUGGESTEDTAGS = "SuggestedTags";

	public static final String BYTES = "Bytes";

	public long[] getSize();

	public void setSize(long... size);

	public Matrix getAsMatrix();

	public void addMatrix(Matrix m);

	public int getMatrixCount();

	public Matrix getMatrix();

	public String getAsString();

	public Matrix getMatrix(int index);

	public long getRowCount();

	public long getColumnCount();

	public Variable clone();

}