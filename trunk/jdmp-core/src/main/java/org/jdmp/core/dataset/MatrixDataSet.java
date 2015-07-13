/*
 * Copyright (C) 2008-2015 by Holger Arndt
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

package org.jdmp.core.dataset;

import java.util.HashMap;
import java.util.Map;

import org.jdmp.core.sample.MatrixSample;
import org.jdmp.core.sample.Sample;
import org.ujmp.core.Matrix;
import org.ujmp.core.util.MathUtil;

public class MatrixDataSet extends AbstractListDataSet {
	private static final long serialVersionUID = -2697648740183157641L;

	public static final int MAXSAMPLES = 10000;

	private final Map<Integer, Sample> sampleBuffer = new HashMap<Integer, Sample>();

	public MatrixDataSet(Matrix input) {
		setMetaData(INPUT, input);
	}

	@Override
	public synchronized Sample get(int index) {
		Sample sample = sampleBuffer.get(index);
		if (sample == null) {
			sample = new MatrixSample(this, index);
			sampleBuffer.put(index, sample);
			while (sampleBuffer.size() > MAXSAMPLES) {
				sampleBuffer.remove(sampleBuffer.keySet().iterator().next());
			}
		}
		return sample;
	}

	@Override
	public boolean addToList(Sample t) {
		throw new RuntimeException("number of samples cannot be changed");
	}

	@Override
	public void addToList(int index, Sample element) {
		throw new RuntimeException("number of samples cannot be changed");
	}

	@Override
	public Sample removeFromList(int index) {
		throw new RuntimeException("number of samples cannot be changed");
	}

	@Override
	public boolean removeFromList(Object o) {
		throw new RuntimeException("number of samples cannot be changed");
	}

	@Override
	public Sample setToList(int index, Sample element) {
		throw new RuntimeException("number of samples cannot be changed");
	}

	@Override
	public void clearList() {
		throw new RuntimeException("number of samples cannot be changed");
	}

	@Override
	public int size() {
		return MathUtil.longToInt(getMetaDataMatrix(INPUT).getRowCount());
	}

	@Override
	public ListDataSet clone() {
		throw new RuntimeException("not implemented");
	}

}
