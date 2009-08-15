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

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.jdmp.core.algorithm.AbstractAlgorithm;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.util.StringUtil;

public class Cd extends AbstractAlgorithm {
	private static final long serialVersionUID = 9205990622002828042L;

	public static final String DESCRIPTION = "changes the directory";

	public Cd(Variable... variables) {
		super();
		setDescription(DESCRIPTION);
		addVariableKey(SOURCE);
		addVariableKey(TARGET);
		setEdgeLabel(SOURCE, "Source");
		setEdgeLabel(TARGET, "Target");
		setEdgeDirection(SOURCE, EdgeDirection.Incoming);
		setEdgeDirection(TARGET, EdgeDirection.Outgoing);
		setVariables(variables);
	}

	@Override
	public Map<String, Object> calculateObjects(Map<String, Object> input) throws MatrixException {
		Map<String, Object> result = new HashMap<String, Object>();
		String in = StringUtil.convert(input.get(SOURCE));

		String ret = null;

		if (in == null || "".equals(in)) {
			ret = java.lang.System.getProperty("user.home");
			java.lang.System.setProperty("user.dir", ret);
		} else if (in.startsWith(".")) {
			String dir = java.lang.System.getProperty("user.dir") + File.separator + in;
			File file = new File(dir);
			ret = file.getAbsolutePath();
			java.lang.System.setProperty("user.dir", ret);
		} else {
			File file = new File(in);
			ret = file.getAbsolutePath();
			java.lang.System.setProperty("user.dir", ret);
		}

		result.put(TARGET, ret);

		return result;
	}

}
