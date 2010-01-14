/*
 * Copyright (C) 2008-2010 by Holger Arndt
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

package org.jdmp.core.sample;

import java.util.Collection;

import org.ujmp.core.Matrix;
import org.ujmp.core.listmatrix.DefaultListMatrix;

public class RelationalSample extends DefaultSample {
	private static final long serialVersionUID = -277834378747296531L;

	public RelationalSample() {
		super();
	}

	@SuppressWarnings("unchecked")
	public void addObject(Object o) {
		Matrix input = getVariables().getMatrix(INPUT);
		if (input == null) {
			input = new DefaultListMatrix<Object>();
			getVariables().setMatrix(INPUT, input);
		}
		if (!(input instanceof Collection<?>)) {
			input = new DefaultListMatrix<Object>(input);
			getVariables().setMatrix(INPUT, input);
		}
		((Collection) input).add(o);
	}

	public void removeObject(Object o) {
		Matrix input = getVariables().getMatrix(INPUT);
		if (input == null) {
			input = new DefaultListMatrix<Object>();
			getVariables().setMatrix(INPUT, input);
		}
		if (!(input instanceof Collection<?>)) {
			input = new DefaultListMatrix<Object>(input);
			getVariables().setMatrix(INPUT, input);
		}
		((Collection<?>) input).remove(o);
	}

	public Collection<?> getObjects() {
		Matrix input = getVariables().getMatrix(INPUT);
		if (input == null) {
			input = new DefaultListMatrix<Object>();
			getVariables().setMatrix(INPUT, input);
		}
		if (!(input instanceof Collection<?>)) {
			input = new DefaultListMatrix<Object>(input);
			getVariables().setMatrix(INPUT, input);
		}
		return (Collection<?>) input;
	}

}
