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

import org.jdmp.core.sample.Sample;
import org.jdmp.jetty.html.EmphasizedText;
import org.jdmp.jetty.html.tags.DivTag;
import org.jdmp.jetty.html.tags.LinkTag;
import org.jdmp.jetty.html.tags.PTag;
import org.jdmp.jetty.html.tags.SpanTag;
import org.jdmp.jetty.index.JettyIndexServlet;

public class DefaultSampleDiv extends DivTag {
	private static final long serialVersionUID = 6587788027413384557L;

	public DefaultSampleDiv(int i, Sample sample, String query,
			String... highlightedWords) {
		query = query == null ? "" : query;
		setParameter("class", "sample");
		String label = sample.getLabel();
		String type = sample.getAllAsString("Type");
		String id = sample.getId();
		String idurl = "?id=" + id;

		PTag p = new PTag();

		DivTag title = new DivTag();
		title.setParameter("class", "title");

		SpanTag labelTag = null;
		if (idurl != null) {
			labelTag = new SpanTag(new LinkTag(idurl, new EmphasizedText(label,
					highlightedWords)));
		} else {
			labelTag = new SpanTag(new EmphasizedText(label, highlightedWords));
		}
		labelTag.setParameter("class", "label");
		title.add(labelTag);

		if (type != null && type.length() > 0) {
			SpanTag typeTag = new SpanTag(type);
			typeTag.setParameter("class", "type");
			title.add(typeTag);
		}
		p.add(title);

		p.add(new DescriptionDiv(sample.getDescription(), highlightedWords));
		p.add(JettyIndexServlet.factory.createVariablesDiv(sample,
				highlightedWords));
		p.add(new URLDiv(sample, highlightedWords));
		add(p);
	}
}
