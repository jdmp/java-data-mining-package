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
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdmp.core.algorithm.index.Index;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.variable.Variable;
import org.jdmp.jetty.html.DefaultHtmlFactory;
import org.jdmp.jetty.html.HtmlFactory;
import org.jdmp.jetty.html.Page;
import org.ujmp.core.Matrix;

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

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);

		try {

			String id = request.getParameter("id");
			String deltag = request.getParameter("deltag");

			if (deltag != null) {
				Sample sample = index.getSample(id);
				if (sample != null) {
					Variable tags = sample.getVariables().get("Tags");
					List<Matrix> toDelete = new LinkedList<Matrix>();
					for (Matrix m : tags.getMatrixList()) {
						if (m != null
								&& m.stringValue().equalsIgnoreCase(deltag)) {
							toDelete.add(m);
						}
					}
					for (Matrix m : toDelete) {
						tags.getMatrixList().remove(m);
					}
					System.out.println("updating sample " + id
							+ " deleting tag: " + deltag);
					index.add(sample);
				}
			}

			PrintWriter out = response.getWriter();
			Page page = null;
			if (id != null) {
				Sample sample = index.getSample(id);
				page = factory.createSamplePage(request, sample, index);
			} else {
				page = factory.createIndexPage(request, index);
			}

			out.append(page.toString());
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
