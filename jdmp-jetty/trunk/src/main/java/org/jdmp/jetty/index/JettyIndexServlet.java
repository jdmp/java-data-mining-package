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
import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.variable.Variable;
import org.jdmp.jetty.html.Page;
import org.jdmp.jetty.html.Text;
import org.jdmp.jetty.html.elements.DataSetDiv;
import org.jdmp.jetty.html.tags.BRTag;
import org.jdmp.jetty.html.tags.FormTag;
import org.jdmp.jetty.html.tags.H1Tag;
import org.jdmp.jetty.html.tags.H2Tag;
import org.jdmp.jetty.html.tags.InputSubmitTag;
import org.jdmp.jetty.html.tags.InputTextTag;
import org.jdmp.jetty.html.tags.LinkTag;
import org.ujmp.core.Matrix;
import org.ujmp.core.interfaces.HasLabel;
import org.ujmp.core.util.StringUtil;

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

	private void searchPage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			String query = request.getParameter("q");
			PrintWriter out = response.getWriter();

			int maxid = 100;
			try {
				maxid = Integer.parseInt(request.getParameter("maxid"));
			} catch (Exception e) {
			}
			for (int i = 0; i < maxid; i++) {
				String tag = request.getParameter("tag" + i);
				if (tag != null && tag.length() > 0) {
					tag = tag.trim();
					String[] splitTags = tag.split(",");
					String id = request.getParameter("id" + i);
					Sample sample = index.getSample(id);
					if (sample != null) {
						Variable tags = sample.getVariables().get("Tags");
						for (String t : splitTags) {
							boolean tagFound = false;
							if (tags != null) {
								for (Matrix m : tags.getMatrixList()) {
									if (m != null
											&& m.stringValue()
													.equalsIgnoreCase(t)) {
										tagFound = true;
									}
								}
							}
							if (!tagFound) {
								sample.setObject("Tags", t);
							}
						}
						System.out.println("updating sample " + id
								+ " with new tag: " + tag);
						index.add(sample);
					}
				}
			}

			Page page = new Page("JDMP Search ["
					+ ((HasLabel) index).getLabel() + "]");
			page.add(new H1Tag("JDMP Search [" + ((HasLabel) index).getLabel()
					+ "]"));
			page.add(index.getSize() + " items in index");
			page.add(new BRTag());
			FormTag searchform = new FormTag("/");
			searchform.add(new InputTextTag("q", query));
			searchform.add(new InputSubmitTag("submit", "submit"));
			searchform.add(new BRTag());

			if (query != null) {
				DataSet result = null;
				try {
					result = index.search(query);
				} catch (Exception e) {
					e.printStackTrace();
				}
				searchform
						.add(new DataSetDiv(result, query, query.split("\\s")));
			}

			page.add(searchform);
			out.append(page.toString());

			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getPage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			String id = request.getParameter("id");
			PrintWriter out = response.getWriter();

			Sample sample = index.getSample(id);
			Page page = null;

			if (sample == null) {
				page = new Page("JDMP Search: not found");
				page.add(new H1Tag("not found: " + id));
			} else {
				page = new Page("JDMP Search: " + sample.getLabel());
				page.add(new H1Tag(sample.getLabel()));

				for (Object key : sample.getVariables().keySet()) {
					if (Sample.LABEL.equals(key)) {
						continue;
					} else if ("Links".equals(key)) {
						Variable links = sample.getVariables().get("Links");
						page.add(new H2Tag(StringUtil.format(key)));
						for (Matrix m : links.getMatrixList().toCollection()) {
							String l = m.stringValue();
							page.add(new LinkTag("/?q=" + l, l));
						}
					} else {
						String value = sample.getAllAsString(key);
						page.add(new H2Tag(StringUtil.format(key)));
						page.add(new Text(value));
					}
				}
			}
			out.append(page.toString());
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
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

			if (id != null && deltag == null) {
				getPage(request, response);
			} else {
				searchPage(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
