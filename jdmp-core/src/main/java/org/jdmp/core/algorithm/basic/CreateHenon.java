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

package org.jdmp.core.algorithm.basic;

import java.util.HashMap;
import java.util.Map;

import org.jdmp.core.algorithm.AbstractAlgorithm;
import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.util.MathUtil;

public class CreateHenon extends AbstractAlgorithm {
	private static final long serialVersionUID = -8708014294068489839L;

	public static final String DESCRIPTION = "generates a HenonMap DataSet";

	public static final String SAMPLECOUNT = "SampleCount";
	public static final String INPUTLENGTH = "InputLength";
	public static final String TARGETLENGTH = "TargetLength";

	public CreateHenon(Variable... variables) {
		super();
		setDescription(DESCRIPTION);
		addVariableKey(SAMPLECOUNT);
		addVariableKey(INPUTLENGTH);
		addVariableKey(TARGETLENGTH);
		addVariableKey(TARGET);
		setEdgeLabel(SAMPLECOUNT, "SampleCount");
		setEdgeLabel(INPUTLENGTH, "InputLength");
		setEdgeLabel(TARGETLENGTH, "TargetLength");
		setEdgeLabel(TARGET, "Target");
		setEdgeDirection(SAMPLECOUNT, EdgeDirection.Incoming);
		setEdgeDirection(INPUTLENGTH, EdgeDirection.Incoming);
		setEdgeDirection(TARGETLENGTH, EdgeDirection.Incoming);
		setEdgeDirection(TARGET, EdgeDirection.Outgoing);
	}

	public Map<String, Object> calculateObjects(Map<String, Object> input) {
		Map<String, Object> result = new HashMap<String, Object>();

		int sampleCount = MathUtil.getInt(input.get(SAMPLECOUNT));
		sampleCount = sampleCount == 0 ? 100 : sampleCount;
		int inputLength = MathUtil.getInt(input.get(INPUTLENGTH));
		inputLength = inputLength == 0 ? 10 : inputLength;
		int targetLength = MathUtil.getInt(input.get(TARGETLENGTH));
		targetLength = targetLength == 0 ? 5 : targetLength;

		result.put(TARGET, DataSet.Factory.HenonMap(sampleCount, inputLength, targetLength));
		return result;
	}
}
