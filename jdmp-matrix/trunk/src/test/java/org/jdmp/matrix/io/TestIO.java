package org.jdmp.matrix.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;

import junit.framework.TestCase;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.Matrix.Format;

public abstract class TestIO extends TestCase {

	public abstract Format getFormat();

	public Matrix getMatrix() {
		return MatrixFactory.rand(5, 8);
	}

	public String getLabel() {
		return this.getClass().getSimpleName();
	}

	public void testExportToFile() throws Exception {

		File file = File.createTempFile("testExportToFile", "." + getFormat().name().toLowerCase());
		file.deleteOnExit();

		Matrix m = getMatrix();
		m.exportToFile(getFormat(), file);

		assertTrue(getLabel(), file.exists());
		assertTrue(getLabel(), file.length() > 0);

		Matrix m2 = MatrixFactory.importFromFile(getFormat(), file);

		assertTrue(getLabel(), m.equalsContent(m2));

		file.delete();
		assertFalse(getLabel(), file.exists());

	}

	public void testExportToStream() throws Exception {

		File file = File.createTempFile("testExportToStream" + getFormat(), "."
				+ getFormat().name().toLowerCase());
		file.deleteOnExit();

		OutputStream os = new FileOutputStream(file);

		Matrix m = getMatrix();
		m.exportToStream(getFormat(), os);

		os.close();

		assertTrue(getLabel(), file.exists());
		assertTrue(getLabel(), file.length() > 0);

		file.delete();
		assertFalse(getLabel(), file.exists());

	}

	public void testExportToWriter() throws Exception {

		File file = File.createTempFile("testExportToWriter", "."
				+ getFormat().name().toLowerCase());
		file.deleteOnExit();

		FileWriter fw = new FileWriter(file);

		Matrix m = getMatrix();
		m.exportToWriter(getFormat(), fw);

		fw.close();

		assertTrue(getLabel(), file.exists());
		assertTrue(getLabel(), file.length() > 0);

		file.delete();
		assertFalse(getLabel(), file.exists());

	}

	public void testExportToString() throws Exception {

		Matrix m = getMatrix();
		String s = m.toString(getFormat());

		assertTrue(getLabel(), s != null);
		assertTrue(getLabel(), s.length() > 0);

	}

	public void testExportToClipboard() throws Exception {

		Matrix m = getMatrix();
		m.exportToClipboard(getFormat());

	}

}
