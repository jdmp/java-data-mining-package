/*
 * Copyright (C) 2008-2013 by Holger Arndt
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
import org.jdmp.core.module.ModuleFactory;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.exceptions.MatrixException;

public class CreateModule extends AbstractAlgorithm {
	private static final long serialVersionUID = 460330426381990987L;

	public static final String DESCRIPTION = "creates a new module";

	public CreateModule(Variable... variables) {
		super();
		setDescription(DESCRIPTION);
		addVariableKey(TARGET);
		setEdgeLabel(TARGET, "Target");
		setEdgeDirection(TARGET, EdgeDirection.Outgoing);
		setVariables(variables);
	}

	
	public Map<String, Object> calculateObjects(Map<String, Object> input) throws MatrixException {
		Map<String, Object> result = new HashMap<String, Object>();

		result.put(TARGET, ModuleFactory.emptyModule());
		return result;
	}
}
