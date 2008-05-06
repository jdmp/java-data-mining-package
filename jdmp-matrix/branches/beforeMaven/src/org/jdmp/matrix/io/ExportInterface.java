package org.jdmp.matrix.io;

import java.io.File;

import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.Matrix.Format;

public interface ExportInterface {

	public void exportToFile(Format format, File file, Object... parameters) throws MatrixException;

	public void exportToFile(Format format, String file, Object... parameters) throws MatrixException;

	public void exportToFile(File file, Object... parameters) throws MatrixException;

	public void exportToFile(String file, Object... parameters) throws MatrixException;

	public String toString(Format format) throws MatrixException;

}
