/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
 *
 * This file is part of the Java Data Mining Package (JDMP).
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership and licensing.
 *
 * JDMP is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * JDMP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with JDMP; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.jdmp.matrix.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdmp.matrix.Matrix;

public class ExportMatrixOBJ {

	private static final Logger logger = Logger.getLogger(ExportMatrixOBJ.class.getName());

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
