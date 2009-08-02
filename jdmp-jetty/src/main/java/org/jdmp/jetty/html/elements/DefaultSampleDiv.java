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
import org.jdmp.core.variable.Variable;
import org.jdmp.jetty.html.EmphasizedText;
import org.jdmp.jetty.html.Text;
import org.jdmp.jetty.html.tags.DivTag;
import org.jdmp.jetty.html.tags.InputHiddenTag;
import org.jdmp.jetty.html.tags.InputTextTag;
import org.jdmp.jetty.html.tags.LinkTag;
import org.jdmp.jetty.html.tags.SpanTag;
import org.ujmp.core.Matrix;
import org.ujmp.core.util.StringUtil;

public class DefaultSampleDiv extends DivTag {
	private static final long serialVersionUID = 6587788027413384557L;

	public DefaultSampleDiv(int i, Sample sample, String query,
			String... highlightedWords) {
		setParameter("class", "sample");
		String label = sample.getLabel();
		String type = sample.getAllAsString("Type");
		String description = sample.getDescription();
		String url = sample.getAsString("URL");
		String id = sample.getId();
		String idurl = "/?id=" + id;

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
		add(title);

		DivTag tagsDiv = new DivTag();
		tagsDiv.setParameter("class", "tags");
		SpanTag tagTag = new SpanTag("Tags:");
		tagTag.setParameter("class", "key");
		tagsDiv.add(tagTag);

		Variable tags = sample.getVariables().get("Tags");
		if (tags != null) {
			SpanTag tagsTag = new SpanTag();
			tagsTag.setParameter("class", "value");
			for (int t = 0; t < tags.getMatrixList().getSize(); t++) {
				Matrix tagMatrix = tags.getMatrixList().getElementAt(t);
				if (tagMatrix != null) {
					String tag = tagMatrix.stringValue();
					tagsTag.add(new EmphasizedText(StringUtil.format(tag),
							highlightedWords));
					LinkTag dellink = new LinkTag("/?q=" + query + "&id=" + id
							+ "&deltag=" + tag, "[x]");
					tagsTag.add(dellink);
					if (t < tags.getMatrixList().getSize() - 1) {
						tagsTag.add(new Text(", "));
					}
				}
			}
			tagsDiv.add(tagsTag);
		}

		InputTextTag inputTag = new InputTextTag("tag" + i);
		inputTag.setParameter("style", "border: 0px;");
		tagsDiv.add(inputTag);
		InputHiddenTag inputId = new InputHiddenTag("id" + i, id);
		tagsDiv.add(inputId);
		add(tagsDiv);

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
			} else if ("Content".equals(key)) {
				continue;
			} else if ("Links".equals(key)) {
				continue;
			} else if ("Tags".equals(key)) {
				continue;
			} else if ("URL".equals(key)) {
				continue;
			}
			DivTag field = new DivTag();
			field.setParameter("class", "field");

			SpanTag keyTag = new SpanTag(StringUtil.format(key) + ":");
			keyTag.setParameter("class", "key");
			field.add(keyTag);
			SpanTag valueTag = new SpanTag(new EmphasizedText(StringUtil
					.format(sample.getAllAsString(key)), highlightedWords));
			valueTag.setParameter("class", "value");
			field.add(valueTag);

			fields.add(field);
		}

		add(fields);

		DivTag address = new DivTag();
		address.setParameter("class", "address");

		if (url != null && url.length() > 0) {
			LinkTag link = new LinkTag(url, new EmphasizedText(url,
					highlightedWords));
			link.setParameter("class", "url");
			SpanTag urlTag = new SpanTag(link);
			urlTag.setParameter("class", "url");
			address.add(urlTag);
		}

		add(address);
	}
}
