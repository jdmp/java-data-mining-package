/*
 * Copyright (C) 2008-2009 by Holger Arndt
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

package org.jdmp.jetty.html.tags;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jdmp.jetty.html.Html;
import org.jdmp.jetty.html.Text;

public abstract class AbstractHtmlTag implements HtmlTag {
	private static final long serialVersionUID = -7005029880318280836L;

	private final List<Html> contentList = new LinkedList<Html>();

	private final Map<String, String> parameterMap = new TreeMap<String, String>();

	
	public void add(Html... content) {
		for (Html h : content) {
			contentList.add(h);
		}
	}

	
	public void add(Collection<Html> content) {
		for (Html h : content) {
			contentList.add(h);
		}
	}

	
	public void add(String... content) {
		for (String h : content) {
			contentList.add(new Text(h));
		}
	}

	public void clear() {
		parameterMap.clear();
		contentList.clear();
	}

	
	public void setParameter(String name, String value) {
		parameterMap.put(name, value);
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("<");
		s.append(getTagName());
		for (String name : parameterMap.keySet()) {
			String value = parameterMap.get(name);
			s.append(" ");
			s.append(name);
			s.append("=");
			s.append("\"");
			s.append(value);
			s.append("\"");
		}

		s.append(">");
		for (Html content : contentList) {
			s.append(content);
		}
		s.append("</");
		s.append(getTagName());
		s.append(">\n");

		return s.toString();
	}

}
