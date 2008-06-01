package org.jdmp.matrix.io;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.Matrix.EntryType;
import org.jdmp.matrix.util.IntelligentFileReader;
import org.jdmp.matrix.util.StringUtil;

public abstract class ImportCSV {

	public static final Matrix fromString(String string, Object... parameters)
			throws MatrixException {
		StringReader sr = new StringReader(string);
		IntelligentFileReader r = new IntelligentFileReader(sr);
		Matrix m = fromReader(r);
		r.close();
		return m;
	}

	public static final Matrix fromFile(File file, Object... parameters) throws MatrixException,
			IOException {
		FileReader lr = new FileReader(file);
		Matrix m = fromReader(lr, parameters);
		m.setLabel(file.getAbsolutePath());
		lr.close();
		return m;
	}

	public static final Matrix fromReader(Reader reader, Object... parameters)
			throws MatrixException {

		List<String> rowData = new ArrayList<String>();

		String separator = "[,;\t]";
		if (parameters.length == 1 && parameters[0] instanceof String) {
			separator = (String) parameters[0];
		}
		try {
			Pattern p = Pattern.compile(separator);
			IntelligentFileReader lr = new IntelligentFileReader(reader);
			int rows = 0;
			int cols = 0;
			String line = null;
			while ((line = lr.readLine()) != null) {
				if (line.length() > 0) {
					rowData.add(line);
					int lcols = p.split(line).length;
					if (lcols > cols) {
						cols = lcols;
					}
					if (lcols > 0) {
						rows++;
					}
				}
			}
			lr.close();
			Matrix m = MatrixFactory.zeros(EntryType.STRING, rows, cols);

			int r = 0;
			for (String l : rowData) {
				String[] fields = p.split(l);
				for (int c = fields.length - 1; c != -1; c--) {
					String s = StringUtil.deleteChar(fields[c], ',', 0);
					m.setString(s, r, c);
				}
				r++;
			}

			return m;
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

}
