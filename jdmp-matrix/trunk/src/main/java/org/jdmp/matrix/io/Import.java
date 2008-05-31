package org.jdmp.matrix.io;

import java.io.File;
import java.io.IOException;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.Matrix.Format;

public abstract class Import {

	public static Matrix importFromFile(File file, Object... parameters) throws MatrixException, IOException {
		return importFromFile(Format.CSV, file, parameters);
	}

	public static Matrix importFromFile(Format format, File file, Object... parameters) throws MatrixException, IOException {
		switch (format) {
		case CSV:
			return ImportCSV.importFromFile(file, parameters);
		case XLS:
			return ImportXLS.importFromExcel(file, parameters);
		default:
			throw new MatrixException("Linking to this file type is not supported. Try importing the file.");
		}
	}

	

	public static Matrix fromString(String string, Object... parameters) {
		return ImportCSV.fromString(string,parameters);
	}

	public static Matrix fromString(Format format, String string, Object parameters) throws MatrixException {
		switch (format) {
		case CSV:
            return ImportCSV.fromString(string,parameters);
		case M:
			return ImportM.fromString(string,parameters);
		default:
			throw new MatrixException("Format " + format + " not yet supported.f");
		}
	}
}
