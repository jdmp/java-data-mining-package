package org.jdmp.matrix.io;

import java.io.File;
import java.io.IOException;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.Matrix.Format;
import org.jdmp.matrix.implementations.io.CSVMatrix;
import org.jdmp.matrix.implementations.io.WaveMatrix;

public abstract class Link {

	public static Matrix linkToFile(Format format, File file, Object... parameters)
			throws MatrixException, IOException {
		switch (format) {
		case CSV:
			return new CSVMatrix(file);
		case WAV:
			return new WaveMatrix(file);
		default:
			throw new MatrixException(
					"Linking to this file type is not supported. Try importing the file.");
		}
	}

}
