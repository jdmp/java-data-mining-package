/*
 * Copyright (C) 2008-2011 by Holger Arndt
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

package org.jdmp.core.algorithm.basic;

import java.util.HashMap;
import java.util.Map;

import org.jdmp.core.algorithm.AbstractAlgorithm;
import org.jdmp.core.sample.SampleFactory;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.MathUtil;

public class CreateSample extends AbstractAlgorithm {
	private static final long serialVersionUID = -8708014294068489839L;

	public static final String DESCRIPTION = "creates a sample from data";

	public static final String INPUT = "Input";
	public static final String TARGET = "Target";
	public static final String RESULT = "Result";

	public CreateSample(Variable... variables) {
		super();
		setDescription(DESCRIPTION);
		addVariableKey(INPUT);
		addVariableKey(TARGET);
		addVariableKey(RESULT);
		setEdgeLabel(INPUT, "Input");
		setEdgeLabel(TARGET, "Target");
		setEdgeLabel(RESULT, "Result");
		setEdgeDirection(INPUT, EdgeDirection.Incoming);
		setEdgeDirection(TARGET, EdgeDirection.Incoming);
		setEdgeDirection(RESULT, EdgeDirection.Outgoing);
	}

	
	public Map<String, Object> calculateObjects(Map<String, Object> input) throws MatrixException {
		Map<String, Object> result = new HashMap<String, Object>();

		Matrix in = MathUtil.getMatrix(input.get(INPUT));
		Matrix target = MathUtil.getMatrix(input.get(TARGET));

		org.jdmp.core.sample.Sample s = null;

		if (in == null && target == null) {
			s = SampleFactory.emptySample();
		} else if (in != null && target == null) {
			s = SampleFactory.linkToMatrix(in);
		} else if (in != null && target != null) {
			s = SampleFactory.classificationSample(in, target);
		}

		result.put(RESULT, s);
		return result;
	}
}
