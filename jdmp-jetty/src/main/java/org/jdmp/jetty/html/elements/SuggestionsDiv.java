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

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jdmp.core.algorithm.spellchecker.SpellChecker;
import org.jdmp.jetty.html.Text;
import org.jdmp.jetty.html.tags.DivTag;
import org.jdmp.jetty.html.tags.LinkTag;
import org.jdmp.jetty.html.tags.PTag;

public class SuggestionsDiv extends DivTag {
	private static final long serialVersionUID = 4591852299647797901L;

	public SuggestionsDiv(SpellChecker index, HttpServletRequest request) {
		setParameter("class", "suggestions");

		PTag p = new PTag();

		String query = request.getParameter("q");

		try {
			List<String> suggestions = index.getSuggestions(query, 3);
			if (!suggestions.isEmpty()) {
				p.add("Did you mean:");
				for (int i = 0; i < 3 && i < suggestions.size(); i++) {
					p.add(new Text(" "));
					String search = suggestions.get(i);
					LinkTag link = new LinkTag("?q=" + search, search);
					link.setParameter("title", "search for '" + search + "'");
					p.add(link);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		add(p);
	}
}
