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

package org.jdmp.jetty.coreobject;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdmp.core.module.Module;
import org.jdmp.core.script.Result;
import org.jdmp.jetty.html.HtmlUtils;
import org.jdmp.jetty.html.Page;
import org.jdmp.jetty.html.tags.BRTag;
import org.jdmp.jetty.html.tags.FormTag;
import org.jdmp.jetty.html.tags.H1Tag;
import org.jdmp.jetty.html.tags.InputSubmitTag;
import org.jdmp.jetty.html.tags.InputTextTag;

public class JettyConsoleServlet extends HttpServlet {
	private static final long serialVersionUID = 1786601018402743358L;

	private Module module = null;

	public JettyConsoleServlet(Module o) {
		this.module = o;
	}

	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);

		String command = request.getParameter("command");

		Result result = null;
		try {
			result = module.execute(command);
		} catch (Exception e) {
		}

		PrintWriter out = response.getWriter();

		String path = request.getPathInfo().substring(1);
		String action = null;

		Page page = new Page("Welcome to JDMP");
		page.add(new H1Tag("Welcome to JDMP"));
		page.add("This is a console for ");
		page.add(module.getClass().getSimpleName());
		page.add(" [" + module.getLabel() + "]. ");
		page.add(new BRTag());
		page.add(new BRTag());
		page.add("Command to execute:");
		page.add(new BRTag());
		FormTag form = new FormTag("/console");
		form.add(new InputTextTag("command"));
		form.add(new InputSubmitTag("submit", "submit"));
		page.add(form);
		page.add(new BRTag());

		if (result != null) {
			page.add(new BRTag());
			page.add(HtmlUtils.format(result.toString()));
		}

		out.append(page.toString());

		out.close();
	}
}
