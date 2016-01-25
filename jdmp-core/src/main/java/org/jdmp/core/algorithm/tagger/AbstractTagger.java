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

package org.jdmp.core.algorithm.tagger;

import org.jdmp.core.algorithm.AbstractAlgorithm;
import org.jdmp.core.algorithm.tokenizer.Tokenizer;
import org.jdmp.core.dataset.ListDataSet;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.listmatrix.ListMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;

public abstract class AbstractTagger extends AbstractAlgorithm implements Tagger {
	private static final long serialVersionUID = 3955297154098236478L;

	public final void tag(ListDataSet dataSet) throws Exception {
		for (Sample sample : dataSet) {
			tag(sample);
		}
	}

	public final void tag(Sample sample) throws Exception {
		Matrix mv = sample.getAsMatrix(Tokenizer.TOKENIZED);
		Variable v = null;
		if (mv instanceof Variable) {
			v = (Variable) mv;
		}
		if (v == null) {
			throw new RuntimeException("text must be tokenized first");
		}
		for (Matrix m : v) {
			ListMatrix<ListMatrix<MapMatrix<String, Object>>> list = tag(m);
			for (Matrix n : list) {
				sample.put(TAGGED, n);
			}
		}
	}
}
