package org.jdmp.matrix.io;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
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
