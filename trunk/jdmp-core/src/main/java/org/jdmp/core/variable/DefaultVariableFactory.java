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

package org.jdmp.core.variable;

import org.ujmp.core.Matrix;

public class DefaultVariableFactory extends AbstractVariableFactory {

	public final Variable labeledVariable(String label) {
		Variable v = new DefaultVariable();
		v.setLabel(label);
		return v;
	}

	public final Variable emptyVariable() {
		return new DefaultVariable();
	}

	public final Variable singleValue(String label, double value) {
		Variable v = new DefaultVariable();
		v.setLabel(label);
		v.add(Matrix.Factory.linkToValue(value));
		return v;
	}
}
