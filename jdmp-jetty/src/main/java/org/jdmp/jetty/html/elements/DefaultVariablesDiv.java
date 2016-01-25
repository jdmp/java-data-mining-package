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

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.jdmp.core.sample.Sample;
import org.jdmp.core.variable.HasVariableMap;
import org.jdmp.core.variable.Variable;
import org.jdmp.jetty.html.EmphasizedText;
import org.jdmp.jetty.html.Text;
import org.jdmp.jetty.html.tags.BTag;
import org.jdmp.jetty.html.tags.DivTag;
import org.jdmp.jetty.html.tags.LinkTag;
import org.jdmp.jetty.html.tags.SpanTag;
import org.ujmp.core.Matrix;
import org.ujmp.core.util.StringUtil;

public class DefaultVariablesDiv extends DivTag {
	private static final long serialVersionUID = -3857330921351894952L;

	public DefaultVariablesDiv(HttpServletRequest request, String path, HasVariableMap variables,
			String... highlightedWords) {
		this(request, path, variables, new HashSet<String>(Arrays.asList(new String[] { Variable.LABEL,
				Variable.DESCRIPTION, Variable.CONTENT })), highlightedWords);
	}

	public DefaultVariablesDiv(HttpServletRequest request, String path, HasVariableMap variables,
			Set<String> skippedVariables, String... highlightedWords) {
		try {
			setParameter("class", "fields");

			String query = request.getParameter("q");
			query = query == null ? "" : query;

			for (Object key : variables.getVariableMap().keySet()) {
				if (skippedVariables.contains(key)) {
					continue;
				}

				Variable var = variables.getVariableMap().get(key);

				DivTag field = new DivTag();
				field.setParameter("class", "variable");

				LinkTag varlink = new LinkTag(path + "variables/" + var.getId(), StringUtil.format(key));
				SpanTag keyTag = new SpanTag(new BTag(varlink, new Text(":")));
				keyTag.setParameter("class", "key");
				field.add(keyTag);

				if (var != null) {
					for (Matrix matrix : var) {
						if (matrix != null) {
							for (long[] c : matrix.availableCoordinates()) {
								String value = matrix.getAsString(c);
								EmphasizedText text = new EmphasizedText(StringUtil.format(value), highlightedWords);
								String qstring = new String(query + " +" + key + ":" + value).trim();
								LinkTag link = new LinkTag(path + "?q=" + URLEncoder.encode(qstring, "utf-8"), text);
								link.setParameter("title", "search for results containing '" + value + "' in " + key);
								SpanTag valueTag = new SpanTag(link);
								valueTag.setParameter("class", "value");
								field.add(valueTag);
							}
						}
					}
				}

				add(field);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DefaultVariablesDiv(HttpServletRequest request, String path, Sample sample, String... highlightedWords) {
		try {
			setParameter("class", "fields");

			String query = request.getParameter("q");
			query = query == null ? "" : query;

			for (String key : sample.keySet()) {
				// if (skippedVariables.contains(key)) {
				// continue;
				// }

				Matrix varm = sample.getAsMatrix(key);
				if (varm instanceof Variable) {
					Variable var = (Variable) varm;

					DivTag field = new DivTag();
					field.setParameter("class", "variable");

					LinkTag varlink = new LinkTag(path + "variables/" + var.getId(), StringUtil.format(key));
					SpanTag keyTag = new SpanTag(new BTag(varlink, new Text(":")));
					keyTag.setParameter("class", "key");
					field.add(keyTag);

					if (var != null) {
						for (Matrix matrix : var) {
							if (matrix != null) {
								for (long[] c : matrix.availableCoordinates()) {
									String value = matrix.getAsString(c);
									EmphasizedText text = new EmphasizedText(StringUtil.format(value), highlightedWords);
									String qstring = new String(query + " +" + key + ":" + value).trim();
									LinkTag link = new LinkTag(path + "?q=" + URLEncoder.encode(qstring, "utf-8"), text);
									link.setParameter("title", "search for results containing '" + value + "' in "
											+ key);
									SpanTag valueTag = new SpanTag(link);
									valueTag.setParameter("class", "value");
									field.add(valueTag);
								}
							}
						}
					}

					add(field);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
