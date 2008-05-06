package org.jdmp.matrix.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdmp.matrix.Matrix;

public class ExportOBJ {

	private static final Logger logger = Logger.getLogger(ExportOBJ.class.getName());

	public static final File selectFile() {
		return selectFile(null);
	}

	public static final File selectFile(Matrix m) {
		return FileSelector.selectFile("OBJ Files", ".obj");
	}

	public static final void save(File file, Serializable o) {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
			oos.writeObject(o);
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not write to file " + file, e);
		} finally {
			try {
				oos.close();
			} catch (Exception e) {
			}
		}
	}

}
