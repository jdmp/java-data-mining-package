package org.jdmp.matrix.io;

import java.io.File;
import java.util.logging.Logger;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.Matrix.Format;

public class ExportMatlab {

	private static final Logger logger = Logger.getLogger(ExportMatlab.class.getName());

	public static final File selectFile() {
		return selectFile(null);
	}

	public static final File selectFile(Matrix m) {
		return FileSelector.selectFile("Matlab Files", ".m");
	}

	public static final void save(String filename, Matrix m) {
		save(new File(filename), m);
	}

	public static final void save(File file, Matrix m) {

	}

	public static String toString(Matrix m) throws MatrixException {
		String EOL = System.getProperty("line.separator");
		StringBuffer s = new StringBuffer();
		s.append("A = [ ");
		s.append(m.toString(Format.CSV));
		s.append("];" + EOL);
		return s.toString();
	}
}
