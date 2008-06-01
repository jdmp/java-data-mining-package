package org.jdmp.gui.io;

import java.awt.Component;
import java.io.File;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdmp.matrix.io.FileSelector;

public class ExportPDF {

	private static final Logger logger = Logger.getLogger(ExportPDF.class.getName());

	public static final File selectFile() {
		return selectFile(null);
	}

	public static final File selectFile(Component c) {
		return FileSelector.selectFile(c, "PDF Files", ".pdf");
	}

	public static final void save(File file, Component component) {
		try {
			Class<?> c = Class.forName("org.jdmp.itext.ExportPDF");
			Method method = c.getMethod("save", new Class[] { File.class, Component.class });
			method.invoke(null, new Object[] { file, component });
		} catch (Exception e) {
			logger.log(Level.WARNING, "cannot show frame", e);
		}
	}

}
