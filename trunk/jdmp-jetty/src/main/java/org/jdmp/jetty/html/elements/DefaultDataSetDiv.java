/*
 * Copyright (C) 2008-2014 by Holger Arndt
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

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.jdmp.core.dataset.ListDataSet;
import org.jdmp.core.sample.Sample;
import org.jdmp.jetty.html.tags.DivTag;
import org.jdmp.jetty.html.tags.InputHiddenTag;
import org.jdmp.jetty.html.tags.PTag;

public class DefaultDataSetDiv extends DivTag {
	private static final long serialVersionUID = 5873327671800366757L;

	public DefaultDataSetDiv(ListDataSet dataSet, String path, HttpServletRequest request, Object... parameters) {
		setParameter("class", "dataset");

		String query = request.getParameter("q");
		query = query == null ? "" : query;

		String[] highlightedWords = new String[] {};
		if (query != null && query.trim().length() > 0) {
			highlightedWords = query.split("\\s+");
			Set<String> temp = new HashSet<String>();
			for (String s : highlightedWords) {
				if (s != null) {
					s = s.replaceAll("\\+", "");
					if (s.contains(":")) {
						s = s.split(":+")[1];
					}
					s = s.trim();
					if (s.length() > 0) {
						temp.add(s);
					}
				}
			}
			highlightedWords = temp.toArray(new String[temp.size()]);
		}

		PTag p = new PTag();

		if (dataSet != null && !dataSet.isEmpty()) {
			int maxid = 0;
			for (Sample s : dataSet) {
				if (s != null) {
					p.add(new DefaultSampleDiv(request, path, maxid++, s, query, highlightedWords));
				}
			}
			p.add(new InputHiddenTag("maxid", "" + maxid));
		}

		add(p);
	}
}
