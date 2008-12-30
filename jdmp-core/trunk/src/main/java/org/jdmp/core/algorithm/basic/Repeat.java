/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
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
import org.jdmp.core.algorithm.Algorithm;
import org.ujmp.core.Matrix;

public class Repeat extends AbstractAlgorithm {
	private static final long serialVersionUID = 1469854394519674382L;

	public static final String DESCRIPTION = "calls another Algorithm several times";

	private final int REPEATEDALGORITHM = 0;

	private int repeatCount = 1;

	public Repeat() {
		super();
		setDescription(DESCRIPTION);
	}

	@Override
	public Map<Object, Matrix> calculate(Map<Object, Matrix> input) throws Exception {
		Map<Object, Matrix> result = new HashMap<Object, Matrix>();

		for (int i = 0; i < repeatCount; i++) {
			result = getAlgorithms().get(REPEATEDALGORITHM).calculate(input);
		}
		return result;

	}

	public int getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
	}

	public void setRepeatedAlgorithm(Algorithm a) {
		setAlgorithm(REPEATEDALGORITHM, a);
	}

	public Algorithm getRepeatedAlgorithm() {
		return getAlgorithms().get(REPEATEDALGORITHM);
	}
}
