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

package org.jdmp.matrix.io;

import java.io.File;
import java.lang.reflect.Constructor;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.exceptions.MatrixException;

public abstract class ImportMatrixXLS {

	public static final Matrix fromFile(File file, Object... parameters) throws MatrixException {
		Matrix xls = null;
		try {
			int sheet = 0;
			Class<?> c = Class.forName("org.jdmp.jexcelapi.DenseExcelMatrix2D");
			Constructor<?> constr = c.getConstructor(new Class[] { File.class, Integer.TYPE });
			xls = (Matrix) constr.newInstance(new Object[] { file, sheet });
		} catch (Exception e) {
			throw new MatrixException(e);
		}
		return xls;
	}

}
