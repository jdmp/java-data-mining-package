package org.jdmp.matrix.io;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;

public abstract class ExportMAT {

	public static void toFile(File file, Matrix matrix, Object... parameters) throws IOException,
			MatrixException {
		try {
			Class<?> c = Class.forName("org.jdmp.jmatio.ExportMAT");
			Method method = c.getMethod("toFile", new Class[] { File.class, Matrix.class,
					Object[].class });
			method.invoke(null, file, matrix, parameters);
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

}
