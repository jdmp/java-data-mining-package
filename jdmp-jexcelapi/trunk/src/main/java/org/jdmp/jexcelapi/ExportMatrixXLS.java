package org.jdmp.jexcelapi;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import jxl.Workbook;
import jxl.biff.EmptyCell;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.util.StringUtil;

public abstract class ExportMatrixXLS {

	public static void toFile(File file, Matrix matrix, Object... parameters) throws IOException,
			MatrixException {
		try {
			WritableWorkbook writableWorkbook = Workbook.createWorkbook(file);
			WritableSheet sheet = writableWorkbook.createSheet("First Sheet", 0);
			for (long[] c : matrix.allCoordinates()) {
				int row = (int) c[Matrix.ROW];
				int column = (int) c[Matrix.COLUMN];
				Object o = matrix.getObject(c);
				WritableCell cell = null;
				if (o == null) {
					cell = new EmptyCell(column, row);
				} else if (o instanceof Number) {
					cell = new jxl.write.Number(column, row, ((Number) o).doubleValue());
				} else {
					cell = new Label(column, row, StringUtil.convert(o));
				}
				sheet.addCell(cell);
			}
			writableWorkbook.write();
			writableWorkbook.close();
		} catch (WriteException e) {
			throw new MatrixException("could not save to file " + file, e);
		}
	}

	public static void toStream(OutputStream outputStream, Matrix matrix, Object... parameters)
			throws IOException, MatrixException {
		try {
			WritableWorkbook writableWorkbook = Workbook.createWorkbook(outputStream);
			WritableSheet sheet = writableWorkbook.createSheet("First Sheet", 0);
			for (long[] c : matrix.allCoordinates()) {
				int row = (int) c[Matrix.ROW];
				int column = (int) c[Matrix.COLUMN];
				Object o = matrix.getObject(c);
				WritableCell cell = null;
				if (o == null) {
					cell = new EmptyCell(column, row);
				} else if (o instanceof Number) {
					cell = new jxl.write.Number(column, row, ((Number) o).doubleValue());
				} else {
					cell = new Label(column, row, StringUtil.convert(o));
				}
				sheet.addCell(cell);
			}
			writableWorkbook.write();
			writableWorkbook.close();
		} catch (WriteException e) {
			throw new MatrixException("could not save to stream", e);
		}
	}

}
