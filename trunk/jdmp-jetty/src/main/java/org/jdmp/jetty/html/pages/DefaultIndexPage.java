/*
 * Copyright (C) 2008-2011 by Holger Arndt
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

package org.jdmp.jetty.html.pages;

import javax.servlet.http.HttpServletRequest;

import org.jdmp.core.algorithm.index.Index;
import org.jdmp.core.algorithm.spellchecker.SpellChecker;
import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.variable.Variable;
import org.jdmp.jetty.html.Page;
import org.jdmp.jetty.html.elements.DefaultDataSetDiv;
import org.jdmp.jetty.html.elements.ResultDiv;
import org.jdmp.jetty.html.elements.SearchDiv;
import org.jdmp.jetty.html.elements.SuggestionsDiv;
import org.jdmp.jetty.html.tags.FormTag;
import org.ujmp.core.Matrix;
import org.ujmp.core.interfaces.HasLabel;

public class DefaultIndexPage extends Page {
	private static final long serialVersionUID = -168890394671032741L;

	public static final int DEFAULTCOUNT = 25;

	public DefaultIndexPage(Index index, String path,
			HttpServletRequest request, Object... parameters) {
		try {

			int maxid = 100;
			try {
				maxid = Integer.parseInt(request.getParameter("max"));
			} catch (Exception e) {
			}
			for (int i = 0; i < maxid; i++) {
				String tag = request.getParameter("tag" + i);
				if (tag != null && tag.length() > 0) {
					tag = tag.trim();
					String[] splitTags = tag.split(",");
					String id = request.getParameter("id" + i);
					Sample sample = index.getSample(id);
					if (sample != null) {
						Variable tags = sample.getVariables().get(Sample.TAGS);
						for (String t : splitTags) {
							boolean tagFound = false;
							if (tags != null) {
								for (Matrix m : tags.getMatrixList()) {
									if (m != null
											&& m.stringValue()
													.equalsIgnoreCase(t)) {
										tagFound = true;
									}
								}
							}
							if (!tagFound) {
								sample.getVariables().setObject("Tags", t);
							}
						}
						System.out.println("updating sample " + id
								+ " with new tag: " + tag);
						index.add(sample);
					}
				}
			}

			setTitle("JDMP Search [" + ((HasLabel) index).getLabel() + "]");

			FormTag searchform = new FormTag(path);

			searchform.add(new SearchDiv(index, request));
			if (index instanceof SpellChecker) {
				searchform
						.add(new SuggestionsDiv((SpellChecker) index, request));
			}

			int start = 0;
			try {
				start = Integer.parseInt(request.getParameter("start"));
			} catch (Exception e) {
			}

			int count = DEFAULTCOUNT;
			try {
				count = Integer.parseInt(request.getParameter("count"));
			} catch (Exception e) {
			}

			String query = request.getParameter("q");
			if (query != null) {
				DataSet result = null;
				try {
					result = index.search(query, start, count);
				} catch (Exception e) {
					e.printStackTrace();
				}

				searchform.add(new ResultDiv(result, start, count));
				searchform.add(new DefaultDataSetDiv(result, path, request));
			}

			add(searchform);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
