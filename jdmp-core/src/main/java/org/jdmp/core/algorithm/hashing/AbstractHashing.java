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

package org.jdmp.core.algorithm.hashing;

import org.jdmp.core.algorithm.AbstractAlgorithm;
import org.jdmp.core.dataset.ListDataSet;
import org.jdmp.core.sample.Sample;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.util.concurrent.PFor;

public abstract class AbstractHashing extends AbstractAlgorithm implements Hashing {
	private static final long serialVersionUID = 870196284002196140L;

	private String inputLabel;

	public AbstractHashing(String inputLabel) {
		super();
		this.inputLabel = inputLabel;
	}

	public AbstractHashing() {
		this(INPUT);
	}

	public String getInputLabel() {
		return inputLabel;
	}

	public int getFeatureCount(ListDataSet dataSet) {
		return (int) dataSet.iterator().next().getAsMatrix(getInputLabel()).toRowVector(Ret.NEW)
				.getRowCount();
	}

	public void setInputLabel(String inputLabel) {
		this.inputLabel = inputLabel;
	}

	public void hash(final ListDataSet dataSet) {
		new PFor(0, dataSet.size() - 1) {

			@Override
			public void step(int i) {
				Sample sample = dataSet.get(i);
				hash(sample);
			}
		};
	}

	public final void hash(Sample sample) {
		Matrix output = hash(sample.getAsMatrix(getInputLabel()));
		sample.put(HASH, output);
	}

}
