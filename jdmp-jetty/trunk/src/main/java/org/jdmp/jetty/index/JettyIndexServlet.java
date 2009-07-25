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

package org.jdmp.jetty.index;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdmp.core.algorithm.index.Index;
import org.jdmp.core.dataset.DataSet;
import org.jdmp.jetty.html.Page;
import org.jdmp.jetty.html.elements.DataSetDiv;
import org.jdmp.jetty.html.tags.BRTag;
import org.jdmp.jetty.html.tags.FormTag;
import org.jdmp.jetty.html.tags.H1Tag;
import org.jdmp.jetty.html.tags.InputSubmitTag;
import org.jdmp.jetty.html.tags.InputTextTag;

public class JettyIndexServlet extends HttpServlet {
	private static final long serialVersionUID = -529359384170033358L;

	private Index index = null;

	public JettyIndexServlet(Index index) {
		this.index = index;
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);

			String query = request.getParameter("q");

			PrintWriter out = response.getWriter();

			String path = request.getPathInfo().substring(1);
			String action = null;

			Page page = new Page("JDMP Search");
			page.add(new H1Tag("JDMP Search"));
			page.add(index.getSize() + " items in index");
			page.add(new BRTag());
			FormTag form = new FormTag("/");
			form.setParameter("method", "get");
			form.add(new InputTextTag("q", query));
			form.add(new InputSubmitTag("submit", "submit"));
			page.add(form);
			page.add(new BRTag());

			if (query != null) {
				DataSet result = null;
				try {
					result = index.search(query);
				} catch (Exception e) {
					e.printStackTrace();
				}
				page.add(new DataSetDiv(result, query.split("\\s")));
			}

			out.append(page.toString());

			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
