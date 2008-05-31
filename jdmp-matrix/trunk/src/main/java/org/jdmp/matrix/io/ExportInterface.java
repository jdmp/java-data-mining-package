package org.jdmp.matrix.io;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.Matrix.Format;

public interface ExportInterface {

	public void exportToFile(Format format, File file, Object... parameters)
			throws MatrixException, IOException;

	public void exportToFile(Format format, String file, Object... parameters)
			throws MatrixException, IOException;

	public void exportToFile(File file, Object... parameters) throws MatrixException, IOException;

	public void exportToFile(String file, Object... parameters) throws MatrixException, IOException;

	public void exportToStream(Format format, OutputStream stream, Object... parameters)
			throws MatrixException, IOException;

	public void exportToWriter(Format format, Writer writer, Object... parameters)
			throws MatrixException, IOException;

	public void exportToClipboard(Format format, Object... parameters) throws MatrixException,
			IOException;

	public String toString(Format format, Object... parameters) throws MatrixException, IOException;

}
