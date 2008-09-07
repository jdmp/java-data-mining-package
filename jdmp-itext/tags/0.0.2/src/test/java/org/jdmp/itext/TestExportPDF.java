package org.jdmp.itext;

import java.io.File;

import javax.swing.JButton;

import junit.framework.TestCase;

public class TestExportPDF extends TestCase {

	public void testExport() {
		JButton b = new JButton("test");
		b.setSize(100, 100);
		File file = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + "test.pdf");
		ExportPDF.save(file, b);

		assertTrue(file.exists());
		assertTrue(file.length() > 0);

		file.delete();

		assertFalse(file.exists());

	}

}
