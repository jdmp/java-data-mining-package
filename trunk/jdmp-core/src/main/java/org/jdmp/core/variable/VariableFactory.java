/*
 * Copyright (C) 2008-2009 by Holger Arndt
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

import java.lang.reflect.Constructor;

import org.ujmp.core.MatrixFactory;
import org.ujmp.core.exceptions.MatrixException;

public abstract class VariableFactory {

	public static final Variable labeledVariable(String label) {
		Variable v = new DefaultVariable();
		v.setLabel(label);
		return v;
	}

	public static final Variable emptyVariable() {
		return new DefaultVariable();
	}

	public static final Variable sharedInstance(String label) {
		try {
			Class<?> c = Class.forName("org.jdmp.jgroups.ReplicatedVariable");
			Constructor<?> con = c.getConstructor(String.class);
			return (Variable) con.newInstance(label);
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	public static final Variable singleValue(String label, double value) {
		Variable v = new DefaultVariable(1);
		v.setLabel(label);
		v.addMatrix(MatrixFactory.linkToValue(value));
		return v;
	}

	public static final Variable labeledVariable(String label, int memorySize) {
		Variable v = new DefaultVariable(memorySize);
		v.setLabel(label);
		return v;
	}

}
