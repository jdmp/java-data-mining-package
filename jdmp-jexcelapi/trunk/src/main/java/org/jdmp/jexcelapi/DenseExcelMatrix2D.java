package org.jdmp.jexcelapi;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.biff.EmptyCell;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.jdmp.matrix.DefaultAnnotation;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.interfaces.Annotation;
import org.jdmp.matrix.stubs.AbstractDenseStringMatrix2D;

public class DenseExcelMatrix2D extends AbstractDenseStringMatrix2D implements Closeable {
	private static final long serialVersionUID = -5250390126697486050L;

	private WritableWorkbook writableWorkbook = null;

	private Workbook workbook = null;

	private Sheet sheet = null;

	private File file = null;

	private int offset = 0;

	private int sheetNumber = 0;

	private Annotation annotation = null;

	private long[] size = null;

	public DenseExcelMatrix2D(long... size) throws MatrixException, IOException {
		this(null, 0, false);
		this.size = size;
	}

	public DenseExcelMatrix2D(Matrix m) throws MatrixException, IOException {
		this(null, 0, false);
		for (long[] c : m.allCoordinates()) {
			setString(m.getString(c), c);
		}
		this.size = m.getSize();
	}

	public DenseExcelMatrix2D(String filename) throws MatrixException, IOException {
		this(filename, 0);
	}

	public DenseExcelMatrix2D(File file) throws MatrixException, IOException {
		this(file, 0);
	}

	public DenseExcelMatrix2D(String filename, boolean labelInFirstRow) throws MatrixException, IOException {
		this(new File(filename), 0, labelInFirstRow);
	}

	public DenseExcelMatrix2D(String filename, int sheetNumber) throws MatrixException, IOException {
		this(new File(filename), sheetNumber, false);
	}

	public DenseExcelMatrix2D(File file, int sheetNumber) throws MatrixException, IOException {
		this(file, sheetNumber, false);
	}

	public DenseExcelMatrix2D(File file, int sheetNumber, boolean labelsInFistLine) throws MatrixException, IOException {
		openFile(file, sheetNumber, labelsInFistLine ? 1 : 0);
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

	private void openFile(File file, int sheetNumber, int offset) throws MatrixException, IOException {
		if (file == null) {
			file = File.createTempFile("jdmpMatrix", ".xls");
			file.delete();
			file.deleteOnExit();
		}

		this.file = file;
		this.offset = offset;
		this.sheetNumber = sheetNumber;

		if (file.exists() && file.length() > 0) {
			try {
				workbook = Workbook.getWorkbook(file);
			} catch (BiffException e) {
				throw new MatrixException(e);
			}
			sheet = workbook.getSheet(sheetNumber);
		} else {
			writableWorkbook = Workbook.createWorkbook(file);
			sheet = writableWorkbook.createSheet("First Sheet", 0);
		}

		annotation = new ExcelAnnotation();
		annotation.setMatrixAnnotation(file + " (" + sheet.getName() + ")");

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
		if (size == null) {
			size = new long[] { sheet.getRows() - offset, sheet.getColumns() };
		}
		return size;
	}

	public String getString(long... coordinates) {
		try {
			Cell c = sheet.getCell((int) coordinates[COLUMN], (int) coordinates[ROW] + offset);
			if (c instanceof EmptyCell) {
				return null;
			} else {
				return c.getContents();
			}
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not read value", e);
		}
		return null;
	}

	public void setString(String string, long... coordinates) {
		try {
			if (sheet instanceof WritableSheet) {
				Label l = new Label((int) coordinates[COLUMN], (int) coordinates[ROW], string);
				((WritableSheet) sheet).addCell(l);
			}
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not write to cell", e);
		}
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		file = (File) s.readObject();
		offset = (Integer) s.readObject();
		size = (long[]) s.readObject();
		Matrix m = (Matrix) s.readObject();
		openFile(file, sheetNumber, offset);
		for (long[] c : m.allCoordinates()) {
			setString(m.getString(c), c);
		}
	}

	private void writeObject(ObjectOutputStream s) throws IOException, MatrixException {
		s.writeObject(file);
		s.writeObject(offset);
		s.writeObject(size);
		s.writeObject(MatrixFactory.copyOf(this));
	}

	public Object getMatrixAnnotation() {
		return annotation.getMatrixAnnotation();
	}

	public void setMatrixAnnotation(Object value) {
		annotation.setMatrixAnnotation(value);
	}

	public Object getAxisAnnotation(int axis, int positionOnAxis) {
		return annotation.getAxisAnnotation(axis, positionOnAxis);
	}

	public void setAxisAnnotation(int axis, int positionOnAxis, Object value) {
		annotation.setAxisAnnotation(axis, positionOnAxis, value);
	}

	public Object getAxisAnnotation(int axis) {
		return annotation.getAxisAnnotation(axis);
	}

	public void setAxisAnnotation(int axis, Object value) {
		annotation.setAxisAnnotation(axis, value);
	}

	class ExcelAnnotation implements Annotation {
		private static final long serialVersionUID = 3671124548385135323L;

		private Object matrixAnnotation = null;

		private Map<Integer, Map<Integer, Object>> axisAnnotation = null;

		private Map<Integer, Object> axisLabelAnnotation = null;

		public ExcelAnnotation() {
			axisAnnotation = new HashMap<Integer, Map<Integer, Object>>();
			axisLabelAnnotation = new HashMap<Integer, Object>(2);
		}

		public void setAxisAnnotation(int axis, int positionOnAxis, Object value) {
			Map<Integer, Object> axisMap = axisAnnotation.get(axis);
			if (axisMap == null) {
				axisMap = new HashMap<Integer, Object>(2);
				axisAnnotation.put(axis, axisMap);
			}
			axisMap.put(positionOnAxis, value);
		}

		public Object getAxisAnnotation(int axis, int positionOnAxis) {
			Map<Integer, Object> axisMap = axisAnnotation.get(axis);
			if (axisMap != null) {
				return axisMap.get(positionOnAxis);
			}
			return null;
		}

		public Object getAxisAnnotation(int axis) {
			return axisLabelAnnotation.get(axis);
		}

		public void setAxisAnnotation(int axis, Object value) {
			axisLabelAnnotation.put(axis, value);
		}

		public Object getMatrixAnnotation() {
			return matrixAnnotation;
		}

		public void setMatrixAnnotation(Object matrixAnnotation) {
			this.matrixAnnotation = matrixAnnotation;
		}

		public Annotation clone() {
			Annotation a = new DefaultAnnotation(this);
			return a;
		}

		public Map<Integer, Map<Integer, Object>> getAxisAnnotation() {
			return axisAnnotation;
		}

		public Map<Integer, Object> getAxisLabelAnnotation() {
			return axisLabelAnnotation;
		}

	}

}
