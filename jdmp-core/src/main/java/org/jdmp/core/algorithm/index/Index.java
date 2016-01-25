/*
 * Copyright (C) 2008-2016 by Holger Arndt
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

package org.jdmp.core.algorithm.index;

import java.util.Map;

import org.jdmp.core.dataset.ListDataSet;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.mapmatrix.MapMatrix;

public interface Index {

	public static final String INPUT = Variable.INPUT;

	public static final String SCORE = Variable.SCORE;

	public void add(Map<String, String> map) throws Exception;

	public void add(MapMatrix<String, String> map) throws Exception;

	public void add(Matrix input) throws Exception;

	public void add(Sample sample) throws Exception;

	public void add(ListDataSet dataSet) throws Exception;

	public ListDataSet search(String query, int start, int count) throws Exception;

	public ListDataSet search(String query) throws Exception;

	public ListDataSet search(String query, int count) throws Exception;

	public int getSize() throws Exception;

	public Sample getSample(String id) throws Exception;

	public int countResults(String query) throws Exception;

}
