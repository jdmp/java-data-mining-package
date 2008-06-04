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
import org.jdmp.matrix.Matrix.Format;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.io.util.IntelligentFileWriter;
import org.jdmp.matrix.util.StringUtil;

public class ExportMatrixTEX {

	private static final Logger logger = Logger.getLogger(ExportMatrixTEX.class.getName());

	public static final void save(String texFile, Matrix m, Object... parameters) {
		save(new File(texFile), m);
	}

	public static final void save(File texFile, Matrix m, Object... parameters) {
		String EOL = System.getProperty("line.separator");

		try {
			if (!texFile.getAbsolutePath().toLowerCase().endsWith(".tex"))
				texFile = new File(texFile.getAbsolutePath() + ".tex");
			File datFile = new File(texFile.getAbsolutePath().replace(".tex", ".dat"));
			File pltFile = new File(texFile.getAbsolutePath().replace(".tex", ".plt"));
			File epsFile = new File(texFile.getAbsolutePath().replace(".tex", ".eps"));

			String datFilename = datFile.getName();
			IntelligentFileWriter out = new IntelligentFileWriter(pltFile);

			out.write("set terminal epslatex" + EOL);
			out.write("set output \"" + epsFile + "\"" + EOL);
			out.write("set xlabel \"Epoch\"" + EOL);
			out.write("set ylabel \"MSE\"" + EOL);
			out.write("set title \"" + m.getLabel() + "\"" + EOL);
			out.write("plot \"" + datFilename + "\" using 1 title \"Trace 1\" with linespoints");
			// for (int i = 1; i < m.getColumnCount(); i++) {
			// out.write(", \"" + datFilename + "\" using 1:" + (2 + i) + "
			// title \"Trace " + (i + 1)
			// + "\" with linespoints");
			// }
			out.close();

			ExportMatrix.toFile(Format.CSV, datFile, m);
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not save TEX file", e);
		}
	}

	public static String toTEX(Matrix m, Object... parameters) throws MatrixException {
		StringBuffer s = new StringBuffer();
		try {
			appendTo(m, s);
		} catch (IOException e) {
			logger.log(Level.WARNING, "could not convert to TEX", e);
		}
		return s.toString();
	}

	private static void appendTo(Matrix m, Appendable out, Object... parameters)
			throws IOException, MatrixException {

		String EOL = System.getProperty("line.separator");

		long rowCount = m.getRowCount();
		long colCount = m.getColumnCount();

		out.append("\\begin{table}[!ht]" + EOL);
		out.append("\\centering" + EOL);

		if (m.getMatrixAnnotation() != null) {
			out.append("\\caption{" + StringUtil.format(m.getMatrixAnnotation()) + "}" + EOL);
		}

		String alignment = "";
		for (long i = m.getColumnCount() - 1; i != -1; i--) {
			alignment += "c";
		}

		out.append("\\begin{tabular}{" + alignment + "}" + EOL);
		out.append("\\toprule" + EOL);

		for (int row = 0; row < rowCount; row++) {
			for (int col = 0; col < colCount; col++) {
				out.append(StringUtil.format(m.getObject(row, col)));

				if (col < colCount - 1) {
					out.append(" & ");
				}
			}
			out.append(" \\\\" + EOL);
		}

		out.append("\\bottomrule" + EOL);
		out.append("\\end{tabular}" + EOL);
		out.append("\\end{table}" + EOL);
	}
}
