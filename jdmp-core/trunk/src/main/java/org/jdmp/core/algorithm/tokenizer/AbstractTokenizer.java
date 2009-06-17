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

package org.jdmp.core.algorithm.tokenizer;

import java.util.ArrayList;
import java.util.List;

import org.jdmp.core.algorithm.AbstractAlgorithm;
import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.VariableFactory;
import org.ujmp.core.Matrix;

public abstract class AbstractTokenizer extends AbstractAlgorithm implements Tokenizer {
	private static final long serialVersionUID = 3272730075460502945L;

	public final void tokenize(Object variableKey, Sample sample) throws Exception {
		Variable input = sample.getVariables().get(variableKey);
		Variable result = VariableFactory.emptyVariable();
		for (Matrix m : input.getMatrixList()) {
			List<Matrix> r = tokenize(m);
			for (Matrix n : r) {
				result.addMatrix(n);
			}
		}
		sample.getVariables().put(Tokenizer.TOKENIZED, result);
	}

	public final void tokenize(Object variableKey, DataSet dataSet) throws Exception {
		for (Sample sample : dataSet.getSamples()) {
			tokenize(variableKey, sample);
		}
	}

	public final List<Matrix> tokenize(Matrix input) throws Exception {
		List<Matrix> result = new ArrayList<Matrix>();
		for (long[] c : input.availableCoordinates()) {
			String s = input.getAsString(c);
			result.addAll(tokenize(s));
		}
		return result;
	}

}