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

package org.jdmp.core.algorithm.basic;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.jdmp.core.algorithm.AbstractAlgorithm;
import org.jdmp.core.algorithm.AlgorithmMapping;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.listmatrix.DefaultListMatrix;
import org.ujmp.core.listmatrix.ListMatrix;

public class Info extends AbstractAlgorithm {
	private static final long serialVersionUID = 1803332964924212288L;

	public static final String DESCRIPTION = "returns an empty matrix";

	public Info(Variable... variables) {
		super();
		setDescription(DESCRIPTION);
	}

	public Map<String, Object> calculateObjects(Map<String, Object> input) {
		Map<String, Object> result = new HashMap<String, Object>();
		ListMatrix<String> algorithms = AlgorithmMapping.list();
		ListMatrix<String> descriptions = new DefaultListMatrix<String>();
		for (String s : algorithms) {
			try {
				String cname = AlgorithmMapping.get(s);
				Class<?> c = Class.forName(cname);
				Field f = c.getField("DESCRIPTION");
				descriptions.add("   " + f.get(null));
			} catch (Exception e) {
				descriptions.add("   [this method is not available]");
			}
		}
		result.put(TARGET, Matrix.Factory.horCat(algorithms, descriptions));
		return result;
	}
}
