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

package org.jdmp.core.algorithm.tokenizer;

import org.jdmp.core.algorithm.AbstractAlgorithm;
import org.jdmp.core.dataset.ListDataSet;
import org.jdmp.core.sample.Sample;
import org.ujmp.core.Matrix;
import org.ujmp.core.listmatrix.DefaultListMatrix;
import org.ujmp.core.listmatrix.ListMatrix;

public abstract class AbstractTokenizer extends AbstractAlgorithm implements Tokenizer {
	private static final long serialVersionUID = 3272730075460502945L;

	public final void tokenize(String variableKey, Sample sample) throws Exception {
		Matrix mv = sample.getAsMatrix(variableKey);
		ListMatrix<ListMatrix<String>> r = tokenize(mv);
		for (Matrix n : r) {
			sample.put(Tokenizer.TOKENIZED, n);
		}
	}

	public final void tokenize(String variableKey, ListDataSet dataSet) throws Exception {
		for (Sample sample : dataSet) {
			tokenize(variableKey, sample);
		}
	}

	public final ListMatrix<ListMatrix<String>> tokenize(Matrix input) throws Exception {
		ListMatrix<ListMatrix<String>> result = new DefaultListMatrix<ListMatrix<String>>();
		for (long[] c : input.availableCoordinates()) {
			String s = input.getAsString(c);
			result.addAll(tokenize(s));
		}
		return result;
	}

}
