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

package org.jdmp.jetty.html.pages;

import javax.servlet.http.HttpServletRequest;

import org.jdmp.core.algorithm.similarity.SimilaritySearcher;
import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.variable.Variable;
import org.jdmp.jetty.html.Page;
import org.jdmp.jetty.html.Text;
import org.jdmp.jetty.html.elements.DefaultDataSetDiv;
import org.jdmp.jetty.html.tags.H1Tag;
import org.jdmp.jetty.html.tags.H2Tag;
import org.jdmp.jetty.html.tags.LinkTag;
import org.ujmp.core.Matrix;
import org.ujmp.core.util.StringUtil;

public class DefaultSamplePage extends Page {
	private static final long serialVersionUID = -3370405362982723837L;

	public DefaultSamplePage(Sample sample, String path, HttpServletRequest request, Object... parameters) {
		super();
		if (sample == null) {
			setTitle("JDMP Search: not found");
			add(new H1Tag("sample not found"));
		} else {
			setTitle("JDMP Search: " + sample.getLabel());
			add(new H1Tag(sample.getLabel()));

			for (String key : sample.getVariableMap().keySet()) {
				if (Sample.LABEL.equals(key)) {
					continue;
				} else if (Variable.LINKS.equals(key)) {
					Variable links = sample.getVariableMap().get(Variable.LINKS);
					add(new H2Tag(StringUtil.format(key)));
					for (Matrix m : links.getMatrixList()) {
						String l = m.stringValue();
						add(new LinkTag("?q=" + l, l));
					}
				} else {
					String value = sample.getVariableMap().getAllAsString(key);
					add(new H2Tag(StringUtil.format(key)));
					add(new Text(value));
				}
			}

			try {
				if (parameters != null && parameters.length > 0 && parameters[0] instanceof SimilaritySearcher) {
					SimilaritySearcher index = (SimilaritySearcher) parameters[0];
					DataSet ds = index.searchSimilar(sample, 0, 10);
					if (ds != null && !ds.getSampleMap().isEmpty()) {
						add(new H2Tag("Similar Results"));
						add(new DefaultDataSetDiv(ds, path, request));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
