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

package org.jdmp.core.algorithm.tagger;

import org.jdmp.core.dataset.ListDataSet;
import org.jdmp.core.sample.Sample;
import org.ujmp.core.Matrix;
import org.ujmp.core.listmatrix.ListMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;

public interface Tagger {

	public static final String TAGGED = "Tagged";

	public ListMatrix<ListMatrix<MapMatrix<String, Object>>> tag(String input) throws Exception;

	public ListMatrix<ListMatrix<MapMatrix<String, Object>>> tag(Matrix input) throws Exception;

	public void tag(Sample sample) throws Exception;

	public void tag(ListDataSet dataSet) throws Exception;

}
