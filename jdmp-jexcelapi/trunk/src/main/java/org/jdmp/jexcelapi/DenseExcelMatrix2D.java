package org.jdmp.jexcelapi;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.stubs.AbstractDenseObjectMatrix2D;

public class DenseExcelMatrix2D extends AbstractDenseObjectMatrix2D implements Closeable {
	private static final long serialVersionUID = -5250390126697486050L;

	private WritableWorkbook writableWorkbook = null;

	private Workbook workbook = null;

	private Sheet sheet = null;

	private WritableSheet writableSheet = null;

	private String filename = null;

	private int offset = 0;

	public DenseExcelMatrix2D(long... size) throws MatrixException {
		this(null, 0, false);
	}

	public DenseExcelMatrix2D(Matrix m) throws MatrixException {
		this(null, 0, false);
		for (long[] c : m.allCoordinates()) {
			setObject(m.getObject(c), c);
		}
	}

	public DenseExcelMatrix2D(String filename) throws MatrixException {
		this(filename, 0);
	}

	public DenseExcelMatrix2D(File file) throws MatrixException {
		this(file, 0);
	}

	public DenseExcelMatrix2D(String filename, boolean labelInFirstRow) throws MatrixException {
		this(new File(filename), 0, labelInFirstRow);
	}

	public DenseExcelMatrix2D(String filename, int sheetNumber) throws MatrixException {
		this(new File(filename), sheetNumber, false);
	}

	public DenseExcelMatrix2D(File file, int sheetNumber) throws MatrixException {
		this(file, sheetNumber, false);
	}

	public DenseExcelMatrix2D(File file, int sheetNumber, boolean labelsInFistLine) throws MatrixException {

		try {

			if (file == null) {
				file = File.createTempFile("xlsMatrix", "xls");
				file.delete();
			}

			this.filename = file.getCanonicalPath();
			this.offset = labelsInFistLine ? 1 : 0;
			if (file.exists()) {
				try {
					workbook = Workbook.getWorkbook(file);
				} catch (BiffException e) {
					throw new MatrixException(e);
				}
				sheet = workbook.getSheet(sheetNumber);
			} else {
				writableWorkbook = Workbook.createWorkbook(file);
				writableSheet = writableWorkbook.createSheet("First Sheet", 0);
			}

		} catch (IOException e) {
			throw new MatrixException(e);
		}
	}

	public DenseExcelMatrix2D(File file, Matrix matrix) throws MatrixException, IOException {
		this(file, 0, false);
		for (long[] c : matrix.allCoordinates()) {
			setObject(matrix.getObject(c), c);
		}
	}

	public DenseExcelMatrix2D(File file, Matrix matrix, boolean labelsInFirstLine) throws MatrixException, IOException {
		this(file, 0, labelsInFirstLine);
		for (long[] c : matrix.allCoordinates()) {
			setObject(matrix.getObject(c), c);
		}
	}

	public void close() {
		try {
			if (workbook != null) {
				workbook.close();
			}
			if (writableWorkbook != null) {
				writableWorkbook.write();
				writableWorkbook.close();
			}
		} catch (Exception e) {
			logger.log(Level.WARNING, "error closing file", e);
		}
	}

	public long[] getSize() {
		return new long[] { sheet.getRows() - offset, sheet.getColumns() };
	}

	public Object getObject(long... coordinates) {
		try {
			Cell c = sheet.getCell((int) coordinates[COLUMN], (int) coordinates[ROW] + offset);
			return c.getContents();
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not read value", e);
		}
		return null;
	}

	public void setObject(Object value, long... coordinates) {
		try {
			if (writableSheet != null) {
				if (coordinates.length >= 2) {
					Label l = new Label((int) coordinates[COLUMN], (int) coordinates[ROW], "" + value.toString());
					writableSheet.addCell(l);
				}
			}
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not write to cell", e);
		}
	}

	public Object getMatrixAnnotation() {
		return filename + " (" + sheet.getName() + ")";
	}

	public void setMatrixAnnotation(Object value) {
	}

}
