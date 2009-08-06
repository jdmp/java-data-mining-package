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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jdmp.core.algorithm.index.Index;
import org.jdmp.core.algorithm.spellchecker.SpellChecker;
import org.jdmp.jetty.html.Text;
import org.jdmp.jetty.html.tags.DivTag;
import org.jdmp.jetty.html.tags.LinkTag;
import org.jdmp.jetty.html.tags.PTag;
import org.ujmp.core.util.Sortable;

public class SuggestionsDiv extends DivTag {
	private static final long serialVersionUID = 4591852299647797901L;

	public SuggestionsDiv(SpellChecker index, HttpServletRequest request) {
		setParameter("class", "suggestions");

		PTag p = new PTag();

		String query = request.getParameter("q");
		try {

			List<String> searches = new ArrayList<String>();

			List<Sortable<Integer, String>> list = new ArrayList<Sortable<Integer, String>>();

			String[] parts = query.split("\\s");
			for (int j = 0; j < parts.length; j++) {
				String part = parts[j];
				List<String> results = index.getSuggestions(part, 3);

				if (results != null && !results.isEmpty()) {

					for (int i = 0; i < results.size(); i++) {
						String search = "";
						for (int k = 0; k < parts.length; k++) {
							if (k == j) {
								search += results.get(i);
							} else {
								search += parts[k];
							}
							if (k < parts.length - 1) {
								search += " ";
							}
						}
						searches.add(search);
					}
				}

				for (String s : searches) {
					int count = ((Index) index).countResults(s);
					Sortable<Integer, String> sortable = new Sortable<Integer, String>(
							count, s);
					list.add(sortable);
				}

			}
			Collections.sort(list);
			Collections.reverse(list);

			if (!list.isEmpty() && list.get(0).getComparable() > 0) {
				p.add("Search suggestions:");
				for (int i = 0; i < 3 && i < list.size(); i++) {
					if (list.get(i).getComparable() != 0) {
						p.add(new Text(" "));
						String search = list.get(i).getObject();
						LinkTag link = new LinkTag("?q=" + search, search);
						link.setParameter("title", "search for '" + search
								+ "'");
						p.add(link);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		add(p);
	}
}
