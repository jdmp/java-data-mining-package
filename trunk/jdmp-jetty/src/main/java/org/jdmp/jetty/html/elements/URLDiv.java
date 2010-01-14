/*
 * Copyright (C) 2008-2010 by Holger Arndt
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

public class URLDiv extends DivTag {
	private static final long serialVersionUID = 6835814533701702954L;

	public URLDiv(Sample sample, String... highlightedWords) {
		setParameter("class", "url");

		String url = sample.getVariables().getAsString(Sample.URL);
		if (url != null && url.length() > 0) {
			LinkTag link = new LinkTag(url, new EmphasizedText(url,
					highlightedWords));
			link.setParameter("class", "url");
			link.setParameter("title", "go to " + url);
			SpanTag urlTag = new SpanTag(link);
			urlTag.setParameter("class", "url");
			add(urlTag);
		}

	}

}
