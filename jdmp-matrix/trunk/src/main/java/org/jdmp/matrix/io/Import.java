package org.jdmp.matrix.io;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.Matrix.Format;

public abstract class Import {

	public static Matrix fromFile(File file, Object... parameters) throws MatrixException,
			IOException {
		return fromFile(Export.guessFormat(file), file, parameters);
	}

	public static Matrix fromFile(Format format, File file, Object... parameters)
			throws MatrixException, IOException {
		try {
			Class<?> c = Class.forName("org.jdmp.matrix.io.Import" + format.name());
			Method m = c.getMethod("fromFile", new Class<?>[] { File.class, Object[].class });
			Matrix matrix=(Matrix)m.invoke(null, file,parameters);
			return matrix;
		} catch (Exception e) {
			throw new MatrixException("file format not supported: " + format, e);
		}
	}

	public static Matrix fromString(String string, Object... parameters) {
		return ImportCSV.fromString(string, parameters);
	}

	public static Matrix fromString(Format format, String string, Object parameters)
			throws MatrixException {
		switch (format) {
		case CSV:
			return ImportCSV.fromString(string, parameters);
		case M:
			return ImportM.fromString(string, parameters);
		default:
			throw new MatrixException("Format " + format + " not yet supported.");
		}
	}
}
