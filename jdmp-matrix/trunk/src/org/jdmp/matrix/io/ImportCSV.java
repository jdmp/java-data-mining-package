package org.jdmp.matrix.io;

import java.io.File;
import java.util.regex.Pattern;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.Matrix.EntryType;
import org.jdmp.matrix.util.IntelligentFileReader;
import org.jdmp.matrix.util.StringUtil;

public abstract class ImportCSV {

	public static final Matrix importFromCSV(File file, Object... parameters) throws MatrixException {
		String separator = ",";
		if (parameters.length == 1 && parameters[0] instanceof String) {
			separator = (String) parameters[0];
		}
		try {
			Pattern p = Pattern.compile(separator);
			IntelligentFileReader lr = new IntelligentFileReader(file);
			int rows = 0;
			int cols = 0;
			String line = null;
			while ((line = lr.readLine()) != null) {
				if (line.length() > 0) {
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
			Matrix m = MatrixFactory.zeros(EntryType.OBJECT, rows, cols);
			m.setLabel(file.getAbsolutePath());
			lr = new IntelligentFileReader(file);
			for (int r = 0; r < rows; r++) {
				line = lr.readLine();
				String[] fields = p.split(line);
				for (int c = fields.length - 1; c != -1; c--) {
					String s = StringUtil.deleteChar(fields[c], ',', 0);
					m.setObject(s, r, c);
				}
			}
			lr.close();
			return m;
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

}
