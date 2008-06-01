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

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Method;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.Matrix.Format;
import org.jdmp.matrix.exceptions.MatrixException;

public abstract class Export {

	public static final void toFile(File file, Matrix m, Object... parameters)
			throws MatrixException, IOException {
		toFile(guessFormat(file), file, m, parameters);
	}

	public static final void toFile(String file, Matrix m, Object... parameters)
			throws MatrixException, IOException {
		toFile(guessFormat(new File(file)), file, m, parameters);
	}

	public static final void toFile(Format format, String filename, Matrix matrix,
			Object... parameters) throws MatrixException, IOException {
		toFile(format, new File(filename), matrix, parameters);
	}

	public static final void toFile(Format format, File file, Matrix matrix, Object... parameters)
			throws MatrixException, IOException {
		try {
			Class<?> c = Class.forName("org.jdmp.matrix.io.Export" + format.name());
			Method m = c.getMethod("toFile", new Class<?>[] { File.class, Matrix.class, Object[].class });
			m.invoke(null, file,matrix,parameters);
		} catch (Exception e) {
			throw new MatrixException("file format not supported: " + format, e);
		}
	}
	
	public static Format guessFormat(File file) {
		String filename = file.getAbsolutePath();
		String[] components = filename.split("\\.");
		String suffix = components[components.length - 1];
		if (suffix.equalsIgnoreCase("gz") || suffix.equalsIgnoreCase("z")
				|| suffix.equalsIgnoreCase("gzip") || suffix.equalsIgnoreCase(".zip")
				|| suffix.equalsIgnoreCase(".7zip") || suffix.equalsIgnoreCase(".7z")) {
			suffix = components[components.length - 2];
		}

		for(Format f:Format.values()){
			if (suffix.equalsIgnoreCase(f.name())) {
				return f;
			}	
		}
		
		throw new MatrixException(
				"could not guess file format, please use exportToFile(Format,File,Matrix)");
	}

	public static final String toString(Format format, Matrix matrix, Object... parameters)
			throws MatrixException, IOException {
		StringWriter writer = new StringWriter();
		toWriter(format, writer, matrix, parameters);
		writer.close();
		return writer.toString();
	}

	public static final void toClipboard(Format format, Matrix matrix, Object... parameters)
			throws MatrixException, IOException {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection ms = new StringSelection(toString(format, matrix, parameters));
		clipboard.setContents(ms, ms);
	}

	public static final void toStream(Format format, OutputStream outputStream, Matrix matrix,
			Object... parameters) throws IOException {
		try {
			Class<?> c = Class.forName("org.jdmp.matrix.io.Export" + format.name());
			Method m = c.getMethod("toStream", new Class<?>[] { OutputStream.class, Matrix.class, Object[].class });
			m.invoke(null, outputStream,matrix,parameters);
		} catch (Exception e) {
			throw new MatrixException("stream format not supported: " + format, e);
		}
	}

	public static final void toWriter(Format format, Writer writer, Matrix matrix,
			Object... parameters) {
		try {
			Class<?> c = Class.forName("org.jdmp.matrix.io.Export" + format.name());
			Method m = c.getMethod("toWriter", new Class<?>[] {Writer.class, Matrix.class, Object[].class });
			m.invoke(null, writer,matrix,parameters);
		} catch (Exception e) {
			throw new MatrixException("writer format not supported: " + format, e);
		}
	}
}
