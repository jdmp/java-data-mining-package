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

package org.jdmp.jetty.index;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdmp.core.JDMPCoreObject;
import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.algorithm.index.Index;
import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.module.Module;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.variable.Variable;
import org.jdmp.jetty.html.DefaultHtmlFactory;
import org.jdmp.jetty.html.HtmlFactory;
import org.jdmp.jetty.html.Page;
import org.ujmp.core.interfaces.CoreObject;

public class JettyIndexServlet extends HttpServlet {
	private static final long serialVersionUID = -529359384170033358L;

	public static HtmlFactory factory = new DefaultHtmlFactory();

	public HtmlFactory getFactory() {
		return factory;
	}

	public static void setFactory(HtmlFactory factory) {
		JettyIndexServlet.factory = factory;
	}

	public Index getIndex() {
		return index;
	}

	public void setIndex(Index index) {
		this.index = index;
	}

	private Index index = null;

	public JettyIndexServlet(Index index) {
		this.index = index;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {

			String ref = request.getPathInfo();
			String format = request.getParameter("format");

			response.setCharacterEncoding("UTF-8");
			response.setStatus(HttpServletResponse.SC_OK);

			CoreObject co = ((JDMPCoreObject) index).getData(ref);

			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			String path = "./";
			Page page = null;
			if (co instanceof Sample) {
				page = factory.createSamplePage(request, path, (Sample) co, index);
			} else if (co instanceof Variable) {
				page = factory.createVariablePage(request, path, (Variable) co, index);
			} else if (co instanceof DataSet) {
				page = factory.createDataSetPage(request, path, (DataSet) co, index);
			} else if (co instanceof Module) {
				page = factory.createModulePage(request, path, (Module) co, index);
			} else if (co instanceof Index) {
				page = factory.createIndexPage(request, path, (Index) co);
			} else if (co instanceof Algorithm) {
				page = factory.createAlgorithmPage(request, path, (Algorithm) co);
			} else {
				page = factory.createNotFoundPage(request, path);
			}

			out.append(page.toString());
			out.close();

			// if (false) {
			// if (deltag != null) {
			// Sample sample = index.getSample(id);
			// if (sample != null) {
			// Variable tags = sample.getVariables().get("Tags");
			// List<Matrix> toDelete = new LinkedList<Matrix>();
			// for (Matrix m : tags.getMatrixList()) {
			// if (m != null
			// && m.stringValue().equalsIgnoreCase(deltag)) {
			// toDelete.add(m);
			// }
			// }
			// for (Matrix m : toDelete) {
			// tags.getMatrixList().remove(m);
			// }
			// System.out.println("updating sample " + id
			// + " deleting tag: " + deltag);
			// index.add(sample);
			// }
			// }
			// }

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
