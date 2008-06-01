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
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.util.IntelligentFileWriter;
import org.jdmp.matrix.util.StringUtil;

public class ExportHTML {

	private static final Logger logger = Logger.getLogger(ExportHTML.class.getName());

	public static final File selectFile() {
		return selectFile(null);
	}

	public static final File selectFile(Matrix m) {
		return FileSelector.selectFile("HTML Files", ".html");
	}

	public static String toHTML(Matrix m) throws MatrixException {
		StringBuffer s = new StringBuffer();
		try {
			appendTo(m, s);
		} catch (IOException e) {
			logger.log(Level.WARNING, "could not convert to HTML", e);
		}
		return s.toString();
	}

	private static void appendTo(Matrix m, Appendable out) throws IOException, MatrixException {
		String EOL = System.getProperty("line.separator");

		long rowCount = m.getRowCount();
		long colCount = m.getColumnCount();

		out.append("<table>" + EOL);
		for (int row = 0; row < rowCount; row++) {
			out.append("<tr>" + EOL);
			for (int col = 0; col < colCount; col++) {
				out.append("<td>");
				out.append(StringUtil.format(m.getObject(row, col)));
				out.append("</td>" + EOL);
			}
			out.append("</tr>" + EOL);
		}
		out.append("</table>" + EOL);
	}

	public static final void save(File file, Matrix m) {
		String EOL = System.getProperty("line.separator");
		IntelligentFileWriter out = null;
		try {
			out = new IntelligentFileWriter(file);
			out
					.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">");
			out.append(EOL + EOL + "<body>" + EOL);
			out.append("<html>" + EOL + EOL);
			appendTo(m, out);
			out.append(EOL + "</body>" + EOL);
			out.append(EOL + "</html>" + EOL);
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not write to file " + file, e);
		} finally {
			try {
				out.close();
			} catch (Exception e) {
			}
		}
	}

}
