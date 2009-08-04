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

package org.jdmp.core.algorithm.index;

import java.util.List;

import org.jdmp.core.algorithm.AbstractAlgorithm;
import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.sample.SampleFactory;
import org.ujmp.core.Matrix;

public abstract class AbstractIndex extends AbstractAlgorithm implements Index {
	private static final long serialVersionUID = 8854402303242176900L;

	public AbstractIndex() {
		super();
	}

	@Override
	public void add(Matrix matrix) throws Exception {
		add(SampleFactory.createFromObject(matrix));
	}

	@Override
	public void add(DataSet dataSet) throws Exception {
		for (Sample sample : dataSet.getSamples()) {
			add(sample);
		}
	}

	public List<String> getSuggestions(String word, int count) throws Exception {
		throw new Exception("not implemented");
	}

	@Override
	public void add(Sample sample) throws Exception {
		throw new Exception("cannot add to this index");
	}

	public final DataSet search(String query) throws Exception {
		return search(query, 0, 100);
	}

	public final DataSet search(String query, int count) throws Exception {
		return search(query, 0, count);
	}

	public final DataSet searchSimilar(Sample sample) throws Exception {
		return searchSimilar(sample, 0, 100);
	}

	public final DataSet searchSimilar(Sample sample, int count) throws Exception {
		return searchSimilar(sample, 0, count);
	}

}
