package org.jdmp.matrix.io;

import java.io.File;
import java.util.logging.Logger;

import org.jdmp.matrix.Matrix;

public class ExportXLS {

	private static final Logger logger = Logger.getLogger(ExportXLS.class.getName());

	public static final File selectFile() {
		return selectFile(null);
	}

	public static final File selectFile(Matrix m) {
		return FileSelector.selectFile("Excel Files", ".xls");
	}

	public static final void save(String filename, Matrix m) {
		save(new File(filename), m);
	}

	public static final void save(File file, Matrix m) {

	}

}
