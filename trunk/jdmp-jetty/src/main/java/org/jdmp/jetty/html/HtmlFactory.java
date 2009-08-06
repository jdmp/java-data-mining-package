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

package org.jdmp.jetty.html;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.algorithm.index.Index;
import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.module.Module;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.variable.Variable;
import org.jdmp.jetty.html.tags.DivTag;
import org.ujmp.core.Matrix;

public interface HtmlFactory {

	public Page createSamplePage(Sample sample, HttpServletRequest request,
			Object... parameters) throws ServletException, IOException;

	public Page createAlgorithmPage(Algorithm algorithm,
			HttpServletRequest request, Object... parameters)
			throws ServletException, IOException;

	public Page createIndexPage(Index index, HttpServletRequest request,
			Object... parameters) throws ServletException, IOException;

	public Page createModulePage(Module module, HttpServletRequest request,
			Object... parameters) throws ServletException, IOException;

	public Page createVariablePage(Variable variable,
			HttpServletRequest request, Object... parameters)
			throws ServletException, IOException;

	public Page createDataSetPage(DataSet dataSet, HttpServletRequest request,
			Object... parameters) throws ServletException, IOException;

	public Page createMatrixPage(Matrix matrix, HttpServletRequest request,
			Object... parameters) throws ServletException, IOException;

	public DivTag createDataSetDiv();

	public DivTag createSample();

	public Html createStyle();

}
