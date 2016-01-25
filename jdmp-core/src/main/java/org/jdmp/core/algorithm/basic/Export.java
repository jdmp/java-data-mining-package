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

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.jdmp.core.algorithm.AbstractAlgorithm;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.StringUtil;

public class Export extends AbstractAlgorithm {
	private static final long serialVersionUID = 2371128340113896073L;

	public static final String DESCRIPTION = "export a matrix to a file";

	public static final String FILE = "Filename";

	public static final String FORMAT = "Format";

	public Export(Variable... variables) {
		super();
		setDescription(DESCRIPTION);
		addVariableKey(SOURCE);
		addVariableKey(FILE);
		addVariableKey(FORMAT);
		addVariableKey(TARGET);
		setEdgeLabel(SOURCE, "Source");
		setEdgeLabel(FILE, "Filename");
		setEdgeLabel(FORMAT, "Format");
		setEdgeLabel(TARGET, "Target");
		setEdgeDirection(SOURCE, EdgeDirection.Incoming);
		setEdgeDirection(FILE, EdgeDirection.Incoming);
		setEdgeDirection(FORMAT, EdgeDirection.Incoming);
		setEdgeDirection(TARGET, EdgeDirection.Outgoing);
		setVariables(variables);
	}

	public Map<String, Object> calculateObjects(Map<String, Object> input) {
		try {
			String file = null;

			Map<String, Object> result = new HashMap<String, Object>();

			Matrix matrix = MathUtil.getMatrix(input.get(SOURCE));

			Object o1 = input.get(FILE);
			if (o1 != null) {
				file = StringUtil.format(o1);
			} else {
				file = File.createTempFile("jdmp_matrix", ".CSV").getAbsolutePath();
			}

			matrix.exportTo().file(new File(file)).asDenseCSV();
			result.put(TARGET, matrix);
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
