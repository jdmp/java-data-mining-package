/*
 * Copyright (C) 2008-2015 by Holger Arndt
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

import org.jdmp.core.algorithm.HasAlgorithmMap;
import org.jdmp.core.dataset.HasDataSetMap;
import org.jdmp.core.dataset.ListDataSet;
import org.jdmp.core.module.HasModuleMap;
import org.jdmp.core.module.Module;
import org.jdmp.core.variable.HasVariableMap;
import org.jdmp.jetty.html.Page;
import org.jdmp.jetty.html.tags.BRTag;
import org.jdmp.jetty.html.tags.H1Tag;
import org.jdmp.jetty.html.tags.LinkTag;
import org.ujmp.core.interfaces.CoreObject;

public class JettyCoreObjectServlet extends HttpServlet {
	private static final long serialVersionUID = -2077611823499422360L;

	private CoreObject object = null;

	public JettyCoreObjectServlet(CoreObject o) {
		this.object = o;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);

		PrintWriter out = response.getWriter();

		String path = request.getPathInfo().substring(1);
		String action = null;

		Page page = new Page("Welcome to JDMP");
		page.add(new H1Tag("Welcome to JDMP"));
		page.add("This is a servlet for ");
		page.add(object.getClass().getSimpleName());
		page.add(" [" + object.getLabel() + "]. ");
		page.add(new BRTag());
		page.add("Here is what you can do:");
		page.add(new BRTag());
		page.add(new BRTag());
		page.add(new LinkTag("/", "Home"));
		page.add(new BRTag());
		if (object instanceof Module) {
			page.add(new LinkTag("/console", "Console"));
			page.add(new BRTag());
		}
		if (object instanceof HasModuleMap) {
			page.add(new LinkTag("/modules", "Modules"));
			page.add(new BRTag());
		}
		if (object instanceof HasVariableMap) {
			page.add(new LinkTag("/variables", "Variables"));
			page.add(new BRTag());
		}
		if (object instanceof HasAlgorithmMap) {
			page.add(new LinkTag("/algorithms", "Algorithms"));
			page.add(new BRTag());
		}
		if (object instanceof HasDataSetMap) {
			page.add(new LinkTag("/datasets", "DataSets"));
			page.add(new BRTag());
		}
		if (object instanceof ListDataSet) {
			page.add(new LinkTag("/samples", "Samples"));
			page.add(new BRTag());
		}

		page.add(new BRTag());

		out.append(page.toString());

		out.close();

	}

}
