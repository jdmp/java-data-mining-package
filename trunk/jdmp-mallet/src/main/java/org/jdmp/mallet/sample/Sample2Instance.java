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

package org.jdmp.mallet.sample;

import java.util.List;

import org.jdmp.core.sample.Sample;
import org.jdmp.mallet.matrix.Matrix2FeatureVector;
import org.jdmp.mallet.matrix.Matrix2LabelVector;
import org.ujmp.core.Matrix;

import cc.mallet.pipe.Pipe;
import cc.mallet.types.Alphabet;
import cc.mallet.types.Instance;
import cc.mallet.types.LabelAlphabet;

public class Sample2Instance extends Instance {
	private static final long serialVersionUID = -9050176506592908630L;

	public Sample2Instance(Sample sample, Alphabet inputAlphabet, LabelAlphabet targetAlphabet, Pipe pipe,
			List<Integer> cumSum) {
		this(sample.getVariableMap().getMatrix(Sample.INPUT), sample.getVariableMap().getMatrix(Sample.TARGET),
				inputAlphabet, targetAlphabet, pipe, cumSum);
		setName(sample.getLabel());
		setSource(sample);
	}

	public Sample2Instance(Matrix inputMatrix, Matrix targetMatrix, Alphabet inputAlphabet,
			LabelAlphabet targetAlphabet, Pipe pipe, List<Integer> cumSum) {
		super(null, null, null, null);
		Matrix2FeatureVector input = new Matrix2FeatureVector(inputMatrix, inputAlphabet, cumSum);
		setData(input);
		if (targetMatrix != null) {
			Matrix2LabelVector target = new Matrix2LabelVector(targetMatrix, targetAlphabet);
			setTarget(target);
		}
	}

}
