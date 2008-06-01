package org.jdmp.matrix.io;

import java.io.File;
import java.lang.reflect.Method;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;

public abstract class ImportMAT {

	public static Matrix fromFile(File file, Object... parameters) {
		try {
			Class<?> c = Class.forName("org.jdmp.jmatio.ImportMAT");
			Method method = c.getMethod("fromFile", new Class[] { File.class, Object[].class });
			Matrix matrix = (Matrix) method.invoke(null, file, parameters);
			return matrix;
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

}
