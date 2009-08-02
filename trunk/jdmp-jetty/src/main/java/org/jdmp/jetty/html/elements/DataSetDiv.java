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

package org.jdmp.jetty.html.elements;

import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.sample.Sample;
import org.jdmp.jetty.html.tags.BRTag;
import org.jdmp.jetty.html.tags.DivTag;
import org.jdmp.jetty.html.tags.InputHiddenTag;

public class DataSetDiv extends DivTag {
	private static final long serialVersionUID = 5873327671800366757L;

	public DataSetDiv(DataSet dataSet, String query, int start,
			String... highlightedWords) {
		start = start == 0 ? 1 : start;
		setParameter("class", "dataset");
		if (dataSet == null || dataSet.getSamples().isEmpty()) {
			add("no results found.");
		} else {
			add("Results " + start + " - "
					+ (start + dataSet.getSamples().getSize()) + " of "
					+ dataSet.getAsInt("Total"));
			add(new BRTag());
			add(new BRTag());
			int i = 0;
			for (Sample s : dataSet.getSamples()) {
				if (s != null) {
					add(new DefaultSampleDiv(i++, s, query, highlightedWords));
				}
			}
			add(new InputHiddenTag("maxid", "" + i));
		}
	}
}
