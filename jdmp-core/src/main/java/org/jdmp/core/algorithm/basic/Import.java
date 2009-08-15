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

package org.jdmp.core.algorithm.basic;

import java.util.HashMap;
import java.util.Map;

import org.jdmp.core.algorithm.AbstractAlgorithm;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.enums.FileFormat;
import org.ujmp.core.util.StringUtil;

public class Import extends AbstractAlgorithm {
	private static final long serialVersionUID = -8530782654162802033L;

	public static final String DESCRIPTION = "import a matrix from file";

	private FileFormat defaultFormat = FileFormat.CSV;

	public static final String FORMAT = "Format";

	public Import(Variable... variables) {
		super();
		setDescription(DESCRIPTION);
		addVariableKey(SOURCE);
		addVariableKey(FORMAT);
		addVariableKey(TARGET);
		setEdgeLabel(SOURCE, "Filename");
		setEdgeLabel(FORMAT, "Format");
		setEdgeLabel(TARGET, "Target");
		setEdgeDirection(SOURCE, EdgeDirection.Incoming);
		setEdgeDirection(FORMAT, EdgeDirection.Incoming);
		setEdgeDirection(TARGET, EdgeDirection.Outgoing);
		setVariables(variables);
	}

	@Override
	public Map<String, Object> calculateObjects(Map<String, Object> input) throws Exception {
		FileFormat format = defaultFormat;

		Map<String, Object> result = new HashMap<String, Object>();

		String file = StringUtil.format(input.get(SOURCE));

		Object o2 = input.get(FORMAT);
		if (o2 != null) {
			format = FileFormat.valueOf(StringUtil.format(o2));
		}

		Matrix target = MatrixFactory.importFromFile(format, file);
		result.put(TARGET, target);
		return result;
	}
}
