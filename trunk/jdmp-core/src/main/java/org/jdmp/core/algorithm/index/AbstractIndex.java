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

package org.jdmp.core.algorithm.index;

import java.util.Map;

import org.jdmp.core.algorithm.AbstractAlgorithm;
import org.jdmp.core.dataset.ListDataSet;
import org.jdmp.core.sample.Sample;
import org.ujmp.core.Matrix;
import org.ujmp.core.mapmatrix.DefaultMapMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;

public abstract class AbstractIndex extends AbstractAlgorithm implements Index {
	private static final long serialVersionUID = 8854402303242176900L;

	public AbstractIndex() {
		super();
	}

	public void add(Matrix matrix) throws Exception {
		// add(SampleFactory.createFromObject(matrix));
	}

	public void add(Map<String, String> map) throws Exception {
		MapMatrix<String, String> mapMatrix = new DefaultMapMatrix<String, String>(map);
		add((Matrix) mapMatrix);
	}

	public void add(MapMatrix<String, String> mapMatrix) throws Exception {
		add((Matrix) mapMatrix);
	}

	public void add(ListDataSet dataSet) throws Exception {
		for (Sample sample : dataSet) {
			add(sample);
		}
	}

	public final ListDataSet search(String query) throws Exception {
		return search(query, 0, 100);
	}

	public final ListDataSet search(String query, int count) throws Exception {
		return search(query, 0, count);
	}

	public int countResults(String query) throws Exception {
		throw new Exception("not implemented");
	}

}
