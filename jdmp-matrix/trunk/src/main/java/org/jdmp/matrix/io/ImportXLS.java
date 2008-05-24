package org.jdmp.matrix.io;

import java.io.File;
import java.lang.reflect.Constructor;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;

public abstract class ImportXLS {

	public static final Matrix importFromExcel(File file, Object... parameters)
			throws MatrixException {
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
