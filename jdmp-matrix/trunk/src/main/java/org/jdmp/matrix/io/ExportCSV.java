package org.jdmp.matrix.io;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.util.IntelligentFileWriter;

public abstract class ExportCSV {

	private static String separator = "\t";

	public static void toFile(File file, Matrix matrix, Object... parameters) throws IOException,
			MatrixException {
		IntelligentFileWriter writer = new IntelligentFileWriter(file);
		toWriter(writer, matrix, parameters);
		writer.close();
	}

	public static void toStream(OutputStream outputStream, Matrix matrix, Object... parameters)
			throws IOException, MatrixException {
		OutputStreamWriter writer = new OutputStreamWriter(outputStream);
		toWriter(writer, matrix, parameters);
		writer.close();
	}

	public static void toWriter(Writer writer, Matrix matrix, Object... parameters)
			throws IOException, MatrixException {
		String lineend = System.getProperty("line.separator");
		long rowCount = matrix.getRowCount();
		long colCount = matrix.getColumnCount();
		for (int row = 0; row < rowCount; row++) {
			for (int col = 0; col < colCount; col++) {
				writer.append("" + matrix.getObject(row, col));
				if (col < colCount - 1) {
					writer.append(separator);
				}
			}
			if (row < rowCount - 1) {
				writer.append(lineend);
			}
		}
	}
}
