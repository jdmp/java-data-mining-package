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

public class SerialExecution extends AbstractAlgorithm {
	private static final long serialVersionUID = -3635082316760868682L;

	public SerialExecution() {
		super();
		setDescription("calls other Algorithms in defined order");
	}

	@Override
	public Map<Object, Matrix> calculate(Map<Object, Matrix> input) throws Exception {
		Map<Object, Matrix> result = new HashMap<Object, Matrix>();

		for (Algorithm a : getAlgorithms()) {
			Map<Object, Matrix> r = a.calculate();
			result.putAll(r);
		}

		return result;
	}

}
