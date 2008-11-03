/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
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

import org.jdmp.core.matrix.wrappers.MatrixToMatrixListWrapper;
import org.ujmp.core.Matrix;
import org.ujmp.core.exceptions.MatrixException;

public abstract class VariableFactory {

	public static final Variable fromMatrix(Matrix m) {
		return new DefaultVariable(new MatrixToMatrixListWrapper(m));
	}

	public static final Variable labeledVariable(String label) {
		return new DefaultVariable(label);
	}

	public static final Variable emptyVariable() {
		return new DefaultVariable();
	}

	public static final Variable singleMatrixVariable() {
		return new SingleMatrixVariable();
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

}
