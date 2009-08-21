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

import org.jdmp.core.JDMP;
import org.jdmp.core.algorithm.AbstractAlgorithm;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.exceptions.MatrixException;

public class About extends AbstractAlgorithm {
	private static final long serialVersionUID = 5328096248640416339L;

	public static final String DESCRIPTION = "displays information about JDMP";

	public About(Variable... variables) {
		super();
		setDescription(DESCRIPTION);
	}

	
	public Map<String, Object> calculateObjects(Map<String, Object> input) throws MatrixException {
		Map<String, Object> result = new HashMap<String, Object>();
		String s = "\n";
		s += "Copyright (C) " + JDMP.COPYRIGHT + " " + JDMP.AUTHOR + "\n";
		s += "\n";
		s += "Java Data Mining Package (JDMP) Version:             " + JDMP.JDMPVERSION + "\n";
		s += "Universal Java Matrix Package (UJMP) Version:        " + JDMP.UJMPVERSION + "\n";
		s += "Java Runtime Version:                                "
				+ System.getProperty("java.runtime.version") + "\n";
		s += "\n";
		s += "The Java Data Mining Package (JDMP) is an open source Java library\n";
		s += "for data analysis and machine learning. It uses the Universal Java\n";
		s += "Matrix Package (UJMP) as a mathematical back-end for calculations\n";
		s += "and I/O functions.\n";
		s += "\n";
		result.put(TARGET, s);
		return result;
	}

}
