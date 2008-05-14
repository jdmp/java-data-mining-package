package org.jdmp.matrix.io;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.util.IntelligentFileWriter;

public class ExportCSV {

	private static final Logger logger = Logger.getLogger(ExportCSV.class.getName());

	private static String separator = "\t";

	public static final File selectFile() {
		return selectFile(null);
	}

	public static final File selectFile(Matrix m) {
		return FileSelector.selectFile("CSV Files", ".csv");
	}

	public static String toCSV(Matrix m,Object...parameters) throws MatrixException {
		StringBuffer s = new StringBuffer();
		try {
			appendTo(m, s,parameters);
		} catch (IOException e) {
			logger.log(Level.WARNING, "could not convert to CSV", e);
		}
		return s.toString();
	}

	private static void appendTo(Matrix m, Appendable out,Object...parameters) throws IOException, MatrixException {
		String lineend = System.getProperty("line.separator");
		long rowCount = m.getRowCount();
		long colCount = m.getColumnCount();
		for (int row = 0; row < rowCount; row++) {
			for (int col = 0; col < colCount; col++) {
				out.append("" + m.getObject(row, col));
				if (col < colCount - 1) {
					out.append(separator);
				}
			}
			if (row < rowCount - 1) {
				out.append(lineend);
			}
		}
	}

	public static final void save(File file, Matrix m,Object...parameters) {
		IntelligentFileWriter out = null;
		try {
			out = new IntelligentFileWriter(file);
			appendTo(m, out,parameters);
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not write to file " + file, e);
		} finally {
			try {
				out.close();
			} catch (Exception e) {
			}
		}
	}

}
