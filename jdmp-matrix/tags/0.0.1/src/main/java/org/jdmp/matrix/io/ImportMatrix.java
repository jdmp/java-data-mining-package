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
import java.io.IOException;
import java.lang.reflect.Method;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.Matrix.Format;
import org.jdmp.matrix.exceptions.MatrixException;

public abstract class ImportMatrix {

	public static Matrix fromFile(File file, Object... parameters) throws MatrixException,
			IOException {
		return fromFile(ExportMatrix.guessFormat(file), file, parameters);
	}

	public static Matrix fromFile(Format format, File file, Object... parameters)
			throws MatrixException, IOException {
		try {
			Class<?> c = Class.forName("org.jdmp.matrix.io.ImportMatrix" + format.name());
			Method m = c.getMethod("fromFile", new Class<?>[] { File.class, Object[].class });
			Matrix matrix=(Matrix)m.invoke(null, file,parameters);
			return matrix;
		} catch (Exception e) {
			throw new MatrixException("file format not supported: " + format, e);
		}
	}

	public static Matrix fromString(String string, Object... parameters) {
		return ImportMatrixCSV.fromString(string, parameters);
	}

	public static Matrix fromString(Format format, String string, Object parameters)
			throws MatrixException {
		switch (format) {
		case CSV:
			return ImportMatrixCSV.fromString(string, parameters);
		case M:
			return ImportMatrixM.fromString(string, parameters);
		default:
			throw new MatrixException("Format " + format + " not yet supported.");
		}
	}
}
