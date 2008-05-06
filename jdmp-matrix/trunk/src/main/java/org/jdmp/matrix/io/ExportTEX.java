package org.jdmp.matrix.io;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.util.IntelligentFileWriter;
import org.jdmp.matrix.util.StringUtil;

public class ExportTEX {

	private static final Logger logger = Logger.getLogger(ExportTEX.class.getName());

	public static final File selectFile() {
		return selectFile(null);
	}

	public static final File selectFile(Component c) {
		return FileSelector.selectFile(c, "TEX Files", ".tex");
	}

	public static final void save(String texFile, Matrix m) {
		save(new File(texFile), m);
	}

	public static final void save(File texFile, Matrix m) {
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

			ExportCSV.save(datFile, m);
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not save TEX file", e);
		}
	}

	public static String toTEX(Matrix m) throws MatrixException {
		StringBuffer s = new StringBuffer();
		try {
			appendTo(m, s);
		} catch (IOException e) {
			logger.log(Level.WARNING, "could not convert to TEX", e);
		}
		return s.toString();
	}

	private static void appendTo(Matrix m, Appendable out) throws IOException, MatrixException {

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