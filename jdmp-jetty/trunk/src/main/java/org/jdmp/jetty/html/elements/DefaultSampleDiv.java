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
import org.jdmp.jetty.html.tags.SpanTag;
import org.ujmp.core.util.StringUtil;

public class DefaultSampleDiv extends DivTag {
	private static final long serialVersionUID = 6587788027413384557L;

	public DefaultSampleDiv(Sample sample, String... highlightedWords) {
		setParameter("class", "sample");
		String label = sample.getLabel();
		String type = sample.getAllAsString("Type");
		String description = sample.getDescription();
		String url = sample.getAsString("URL");

		DivTag title = new DivTag();
		title.setParameter("class", "title");

		SpanTag labelTag = null;
		if (url != null && url.contains("://")) {
			labelTag = new SpanTag(new LinkTag(url, new EmphasizedText(label,
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
		add(title);

		if (description != null && description.length() > 0) {
			DivTag descriptionDiv = new DivTag(new EmphasizedText(description,
					highlightedWords));
			descriptionDiv.setParameter("class", "description");
			add(descriptionDiv);
		}

		DivTag fields = new DivTag();
		fields.setParameter("class", "fields");

		for (Object key : sample.getVariables().keySet()) {
			if (Sample.LABEL.equals(key)) {
				continue;
			} else if (Sample.DESCRIPTION.equals(key)) {
				continue;
			} else if ("Type".equals(key)) {
				continue;
			} else if ("URL".equals(key)) {
				continue;
			}
			DivTag field = new DivTag();
			field.setParameter("class", "field");

			SpanTag keyTag = new SpanTag(StringUtil.format(key) + ":");
			keyTag.setParameter("class", "key");
			field.add(keyTag);
			SpanTag valueTag = new SpanTag(StringUtil.format(sample
					.getAllAsString(key)));
			valueTag.setParameter("class", "value");
			field.add(valueTag);

			fields.add(field);
		}

		add(fields);

		DivTag address = new DivTag();
		address.setParameter("class", "address");

		if (url != null && url.length() > 0) {
			SpanTag urlTag = new SpanTag(new EmphasizedText(url,
					highlightedWords));
			urlTag.setParameter("class", "url");
			address.add(urlTag);
		}

		add(address);
	}
}
