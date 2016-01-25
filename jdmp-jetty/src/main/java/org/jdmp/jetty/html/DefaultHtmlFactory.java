/*
 * Copyright (C) 2008-2016 by Holger Arndt
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

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.algorithm.index.Index;
import org.jdmp.core.dataset.ListDataSet;
import org.jdmp.core.module.Module;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.variable.Variable;
import org.jdmp.jetty.html.elements.DefaultVariablesDiv;
import org.jdmp.jetty.html.pages.DefaultAlgorithmPage;
import org.jdmp.jetty.html.pages.DefaultDataSetPage;
import org.jdmp.jetty.html.pages.DefaultIndexPage;
import org.jdmp.jetty.html.pages.DefaultMatrixPage;
import org.jdmp.jetty.html.pages.DefaultModulePage;
import org.jdmp.jetty.html.pages.DefaultNotFoundPage;
import org.jdmp.jetty.html.pages.DefaultSamplePage;
import org.jdmp.jetty.html.pages.DefaultVariablePage;
import org.ujmp.core.Matrix;

public class DefaultHtmlFactory implements HtmlFactory {

	public Html createStyle() {
		return new DefaultStyle();
	}

	public Page createSamplePage(HttpServletRequest request, String path, Sample sample, Object... parameters)
			throws ServletException, IOException {
		return new DefaultSamplePage(sample, path, request, parameters);
	}

	public Page createIndexPage(HttpServletRequest request, String path, Index index, Object... parameters)
			throws ServletException, IOException {
		return new DefaultIndexPage(index, path, request, parameters);
	}

	public Page createAlgorithmPage(HttpServletRequest request, String path, Algorithm algorithm, Object... parameters)
			throws ServletException, IOException {
		return new DefaultAlgorithmPage(request, path, algorithm, parameters);
	}

	public Page createDataSetPage(HttpServletRequest request, String path, ListDataSet dataSet, Object... parameters)
			throws ServletException, IOException {
		return new DefaultDataSetPage(request, path, dataSet, parameters);
	}

	public Page createMatrixPage(HttpServletRequest request, String path, Matrix matrix, Object... parameters)
			throws ServletException, IOException {
		return new DefaultMatrixPage(request, path, matrix, parameters);
	}

	public Page createModulePage(HttpServletRequest request, String path, Module module, Object... parameters)
			throws ServletException, IOException {
		return new DefaultModulePage(request, path, module, parameters);
	}

	public Page createVariablePage(HttpServletRequest request, String path, Variable variable, Object... parameters)
			throws ServletException, IOException {
		return new DefaultVariablePage(request, path, variable, parameters);
	}

	public Html createVariablesDiv(HttpServletRequest request, String path, Sample sample, String... highlightedWords) {
		return new DefaultVariablesDiv(request, path, sample, highlightedWords);
	}

	public Page createNotFoundPage(HttpServletRequest request, String path, Object... parameters) {
		return new DefaultNotFoundPage(request, path);
	}

}
