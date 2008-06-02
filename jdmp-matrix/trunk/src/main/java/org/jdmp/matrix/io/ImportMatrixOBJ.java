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

import java.awt.Component;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.logging.Logger;

public class ImportMatrixOBJ {
	private static final Logger logger = Logger.getLogger(ImportMatrixOBJ.class.getName());

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
