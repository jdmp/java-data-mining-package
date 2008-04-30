package org.jdmp.matrix.io;

import java.io.File;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.Matrix.Format;

public abstract class Export {

	public static final void save(File file, Matrix m, Object... parameters) throws MatrixException {
		save(file.getAbsolutePath(), m);
	}

	public static final void save(String file, Matrix m, Object... parameters) throws MatrixException {
		String[] components = file.split("\\.");
		String suffix = components[components.length - 1];
		if (suffix.equalsIgnoreCase("gz") || suffix.equalsIgnoreCase("z") || suffix.equalsIgnoreCase("gzip")
				|| suffix.equalsIgnoreCase(".zip") || suffix.equalsIgnoreCase(".7zip")
				|| suffix.equalsIgnoreCase(".7z")) {
			suffix = components[components.length - 2];
		}

		if (suffix.equalsIgnoreCase("csv")) {
			save(Format.CSV, new File(file), m);
			return;
		} else {
			throw new MatrixException("could not guess file format, please use exportToFile(Format,File,Matrix)");
		}

	}

	public static final void save(Format format, String file, Matrix m, Object... parameters) throws MatrixException {
		save(format, new File(file), m, parameters);
	}

	public static final void save(Format format, File file, Matrix m, Object... parameters) throws MatrixException {
		switch (format) {
		case CSV:
			ExportCSV.save(file, m);
			return;
		default:
			throw new MatrixException("export to " + format + " no yet supported.");
		}
	}

	public static final String toString(Format format, Matrix m, Object... paramters) throws MatrixException {
		switch (format) {
		case CSV:
			return ExportCSV.toCSV(m);
		}
		return "format " + format + " not yet supported";
	}
}
