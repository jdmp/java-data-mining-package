package org.jdmp.matrix.io;

import java.io.IOException;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.Matrix.Format;

public class ExportMatlab {

	public static String toString(Matrix m, Object... parameters) throws MatrixException,
			IOException {
		String EOL = System.getProperty("line.separator");
		StringBuffer s = new StringBuffer();
		s.append("A = [ ");
		s.append(m.toString(Format.CSV));
		s.append("];" + EOL);
		return s.toString();
	}
}
