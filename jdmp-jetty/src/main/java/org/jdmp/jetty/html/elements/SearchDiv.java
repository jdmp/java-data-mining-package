/*
 * Copyright (C) 2008-2009 by Holger Arndt
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

import javax.servlet.http.HttpServletRequest;

import org.jdmp.core.algorithm.index.Index;
import org.jdmp.jetty.html.tags.BRTag;
import org.jdmp.jetty.html.tags.DivTag;
import org.jdmp.jetty.html.tags.H1Tag;
import org.jdmp.jetty.html.tags.InputSubmitTag;
import org.jdmp.jetty.html.tags.InputTextTag;
import org.jdmp.jetty.html.tags.LinkTag;
import org.jdmp.jetty.html.tags.PTag;
import org.ujmp.core.interfaces.HasLabel;

public class SearchDiv extends DivTag {
	private static final long serialVersionUID = -7692590820809423552L;

	public SearchDiv(Index index, HttpServletRequest request) {
		try {
			setParameter("class", "search");

			String query = request.getParameter("q");

			H1Tag h1 = new H1Tag("JDMP Search ["
					+ ((HasLabel) index).getLabel() + "]");
			LinkTag h1Link = new LinkTag("http://www.jdmp.org", h1);
			h1Link
					.setParameter("title",
							"this search engine is powered by the Java Data Mining Package");
			add(h1Link);

			PTag p = new PTag();
			p.add(index.getSize() + " items in index");
			p.add(new BRTag());

			InputTextTag inputText = new InputTextTag("q", query);
			inputText.setParameter("title", "enter your query here");
			p.add(inputText);
			InputSubmitTag submit = new InputSubmitTag("submit", "submit");
			submit.setParameter("title", "submit query");
			p.add(submit);
			add(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
