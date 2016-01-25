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

package org.jdmp.jetty.html.elements;

import org.jdmp.core.dataset.ListDataSet;
import org.jdmp.core.variable.Variable;
import org.jdmp.jetty.html.tags.DivTag;
import org.jdmp.jetty.html.tags.PTag;

public class ResultDiv extends DivTag {
	private static final long serialVersionUID = -8988055309556601716L;

	public ResultDiv(ListDataSet dataSet, int start, int count) {
		setParameter("class", "result");
		PTag p = new PTag();
		if (dataSet == null || dataSet.isEmpty()) {
			p.add("no results found.");
		} else {
			//p.add("Results " + (start + 1) + " - " + (start + count) + " of "
			//		+ dataSet.getVariableMap().getAsInt(Variable.TOTAL));
		}
		add(p);
	}
}
