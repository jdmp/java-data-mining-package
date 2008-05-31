package org.jdmp.matrix.io;

import java.io.Closeable;
import java.io.File;
import java.lang.reflect.Constructor;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;

public class ExportXLS {

	public static void toFile(File file, Matrix matrix, Object... parameters) {
		Matrix xls = null;
		try {
			Class<?> c = Class.forName("org.jdmp.jexcelapi.DenseExcelMatrix2D");
			Constructor<?> constr = c.getConstructor(new Class[] { File.class, Matrix.class });
			xls = (Matrix) constr.newInstance(new Object[] { file, matrix });
			((Closeable) xls).close();
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

}
