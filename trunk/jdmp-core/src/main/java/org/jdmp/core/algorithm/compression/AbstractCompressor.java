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

package org.jdmp.core.algorithm.compression;

import org.jdmp.core.algorithm.AbstractAlgorithm;
import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.sample.Sample;
import org.ujmp.core.Matrix;

public abstract class AbstractCompressor extends AbstractAlgorithm implements Compressor {
	private static final long serialVersionUID = 870196284002196140L;

	private String inputLabel;

	public AbstractCompressor(String inputLabel) {
		super();
		this.inputLabel = inputLabel;
	}

	public AbstractCompressor() {
		this(INPUT);
	}

	public String getInputLabel() {
		return inputLabel;
	}

	public void setInputLabel(String inputLabel) {
		this.inputLabel = inputLabel;
	}

	public void compress(DataSet dataSet) throws Exception {
		for (Sample sample : dataSet.getSampleMap()) {
			compress(sample);
		}
	}

	public final void compress(Sample sample) throws Exception {
		Matrix output = compress(sample.getMatrix(getInputLabel()));
		sample.setMatrix(COMPRESSED, output);
	}

	public void decompress(DataSet dataSet) throws Exception {
		for (Sample sample : dataSet.getSampleMap()) {
			compress(sample);
		}
	}

	public final void decompress(Sample sample) throws Exception {
		Matrix output = decompress(sample.getMatrix(COMPRESSED));
		sample.setMatrix(DECOMPRESSED, output);
	}

}
