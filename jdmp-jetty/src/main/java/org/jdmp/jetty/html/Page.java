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

package org.jdmp.jetty.html;

import java.util.Collection;

import org.jdmp.jetty.html.tags.BodyTag;
import org.jdmp.jetty.html.tags.HeadTag;
import org.jdmp.jetty.html.tags.TitleTag;

public class Page implements Html {
	private static final long serialVersionUID = -1869180656540675389L;

	private HeadTag head = new HeadTag();

	private TitleTag title = new TitleTag();

	private BodyTag body = new BodyTag();

	private Html style = new DefaultStyle();

	public Page(String titleString, Html... content) {
		setTitle(titleString);
		body.add(content);
	}

	public Page() {
	}

	public void setTitle(String t) {
		title.clear();
		title.add(new Text(t));
		head.clear();
		head.add(title);
		if (style != null) {
			head.add(style);
		}
	}

	public void setStyle(Html style) {
		this.style = style;
		head.clear();
		head.add(title);
		if (style != null) {
			head.add(style);
		}
	}

	public void add(Html... content) {
		body.add(content);
	}

	public void add(Collection<Html> content) {
		body.add(content);
	}

	public void add(String... content) {
		body.add(content);
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		s.append("<html xmlns=\"http://www.w3.org/1999/xhtml\" dir=\"ltr\" lang=\"en-US\">");

		s.append(head);
		s.append(body);

		s.append("</html>");

		return s.toString();
	}
}
