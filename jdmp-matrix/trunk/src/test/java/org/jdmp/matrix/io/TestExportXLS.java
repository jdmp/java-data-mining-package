package org.jdmp.matrix.io;

import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.Matrix.Format;

public class TestExportXLS extends TestIO {

	public Format getFormat() {
		return Format.XLS;
	}

	public void testExportToStream() throws Exception {
		try {
			super.testExportToStream();
		} catch (MatrixException e) {
			return;
		}
		throw new Exception("this method should not be supported");
	}

	public void testExportToWriter() throws Exception {
		try {
			super.testExportToWriter();
		} catch (MatrixException e) {
			return;
		}
		throw new Exception("this method should not be supported");
	}

	public void testExportToClipboard() throws Exception {
		try {
			super.testExportToClipboard();
		} catch (MatrixException e) {
			return;
		}
		throw new Exception("this method should not be supported");
	}

	public void testExportToString() throws Exception {
		try {
			super.testExportToString();
		} catch (MatrixException e) {
			return;
		}
		throw new Exception("this method should not be supported");
	}

}
