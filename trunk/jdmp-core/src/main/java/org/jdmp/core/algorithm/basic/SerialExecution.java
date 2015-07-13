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

package org.jdmp.core.algorithm.basic;

import java.util.HashMap;
import java.util.Map;

import org.jdmp.core.algorithm.AbstractAlgorithm;
import org.jdmp.core.algorithm.Algorithm;

public class SerialExecution extends AbstractAlgorithm {
	private static final long serialVersionUID = -3635082316760868682L;

	public static final String DESCRIPTION = "calls other Algorithms in defined order";

	public SerialExecution() {
		super();
		setDescription(DESCRIPTION);
	}

	public Map<String, Object> calculateObjects(Map<String, Object> input) {
		Map<String, Object> result = new HashMap<String, Object>();

		for (Algorithm a : getAlgorithmMap().values()) {
			Map<String, Object> r = a.calculate();
			result.putAll(r);
		}

		return result;
	}

}
