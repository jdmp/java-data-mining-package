package org.jdmp.matrix.io;

import java.awt.Component;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.logging.Logger;

public class ImportOBJ {
	private static final Logger logger = Logger.getLogger(ImportOBJ.class.getName());

	public static final File selectFile() {
		return selectFile(null);
	}

	public static final File selectFile(Component c) {
		return FileSelector.selectFile(c, "OBJ Files", ".obj");
	}

	@SuppressWarnings("unchecked")
	public static Object load(File file) {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
			Object o = ois.readObject();
			return o;
		} catch (Exception e) {
		} finally {
			try {
				ois.close();
			} catch (Exception e2) {
			}
		}
		return null;
	}
}
