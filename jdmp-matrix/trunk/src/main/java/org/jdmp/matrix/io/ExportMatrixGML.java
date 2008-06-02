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

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.util.IntelligentFileWriter;

public class ExportMatrixGML {

	private static final Logger logger = Logger.getLogger(ExportMatrixGML.class.getName());

	public static final File selectFile() {
		return selectFile(null);
	}

	public static final File selectFile(Matrix m) {
		return FileSelector.selectFile("GraphML Files", ".gml");
	}

	public static final void save(String filename, Matrix m) {
		save(new File(filename), m);
	}

	public static final void save(File file, Matrix m) {
		try {
			IntelligentFileWriter w = new IntelligentFileWriter(file);
			w.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			w.write("<graphml>\n");
			w.write("<key id=\"k0\" for=\"node\" attr.name=\"variableName\" attr.type=\"string\"></key>\n");
			w.write("<key id=\"k1\" for=\"node\" attr.name=\"description\" attr.type=\"string\"></key>\n");
			w
					.write("<key id=\"k2\" for=\"edge\" attr.name=\"confidence\" attr.type=\"double\"><default>0.0</default></key>\n");
			w
					.write("<key id=\"k3\" for=\"edge\" attr.name=\"directed\" attr.type=\"boolean\"><default>false</default></key>\n");
			w.write("<key id=\"k5\" for=\"node\" attr.name=\"xPos\" attr.type=\"string\"></key>\n");
			w.write("<key id=\"k6\" for=\"node\" attr.name=\"yPos\" attr.type=\"string\"></key>\n");

			w.write("<graph id=\"" + m.getLabel() + "\" edgedefault=\"undirected\">\n");

			for (int i = 0; i < m.getRowCount(); i++) {
				w.write("<node id=\"node" + i + "\">\n");
				w.write("<data key=\"k0\">" + m.getRowLabel(i).replaceAll("[<>&]", "") + "</data>\n");
				// w.write("<data key=\"k5\">" + n.getPosX() + "</data>\n");
				// w.write("<data key=\"k6\">" + n.getPosY() + "</data>\n");
				w.write("</node>\n");
			}

			int id = 0;
			for (long[] c : m.allCoordinates()) {
				if (m.getDouble(c) > 0.0) {
					w.write("<edge id=\"edge" + (id++) + "\" ");
					w.write("source=\"node" + c[Matrix.ROW] + "\" ");
					w.write("target=\"node" + c[Matrix.COLUMN] + "\">\n");
					w.write("</edge>\n");
				}
			}
			w.write("</graph>\n");
			w.write("</graphml>");
			w.close();
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not export", e);
		}
	}

}
