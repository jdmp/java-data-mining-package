package org.jdmp.jexcelapi;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import jxl.Cell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.Matrix.EntryType;
import org.ujmp.core.exceptions.MatrixException;

public abstract class ImportMatrixXLS {

	public static final Matrix fromFile(File file, Object... parameters) throws MatrixException,
			IOException {
		try {
			Workbook workbook = Workbook.getWorkbook(file);
			Sheet sheet = workbook.getSheet(0);
			int rows = sheet.getRows();
			int columns = sheet.getColumns();
			Matrix matrix = MatrixFactory.zeros(EntryType.OBJECT, rows, columns);
			for (int row = 0; row < rows; row++) {
				for (int column = 0; column < columns; column++) {
					Cell c = sheet.getCell(column, row);
					Object o = null;
					if (c instanceof NumberCell) {
						o = ((NumberCell) c).getValue();
					} else {
						o = c.getContents();
					}
					matrix.setObject(o, row, column);
				}
			}
			workbook.close();
			return matrix;
		} catch (BiffException e) {
			throw new MatrixException("could not import from file " + file, e);
		}
	}

	public static final Matrix fromStream(InputStream inputStream, Object... parameters)
			throws IOException, MatrixException {
		try {
			Workbook workbook = Workbook.getWorkbook(inputStream);
			Sheet sheet = workbook.getSheet(0);
			int rows = sheet.getRows();
			int columns = sheet.getColumns();
			Matrix matrix = MatrixFactory.zeros(EntryType.OBJECT, rows, columns);
			for (int row = 0; row < rows; row++) {
				for (int column = 0; column < columns; column++) {
					Cell c = sheet.getCell(column, row);
					Object o = null;
					if (c instanceof NumberCell) {
						o = ((NumberCell) c).getValue();
					} else {
						o = c.getContents();
					}
					matrix.setObject(o, row, column);
				}
			}
			workbook.close();
			return matrix;
		} catch (BiffException e) {
			throw new MatrixException("could not import from stream", e);
		}
	}

}
