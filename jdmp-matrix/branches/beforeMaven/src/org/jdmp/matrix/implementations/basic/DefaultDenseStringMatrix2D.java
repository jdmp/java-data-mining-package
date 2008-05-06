package org.jdmp.matrix.implementations.basic;

import org.jdmp.matrix.DefaultAnnotation;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.stubs.AbstractDenseStringMatrix2D;
import org.jdmp.matrix.util.StringUtil;

public class DefaultDenseStringMatrix2D extends AbstractDenseStringMatrix2D {
	private static final long serialVersionUID = -4292004796378125964L;

	private DefaultAnnotation annotation = null;

	private String[][] values = null;

	public DefaultDenseStringMatrix2D(String string) {
		string = string.replaceAll(StringUtil.BRACKETS, "");
		String[] rows = string.split(StringUtil.SEMICOLONORNEWLINE);
		String[] cols = rows[0].split(StringUtil.COLONORSPACES);
		values = new String[rows.length][cols.length];
		for (int r = 0; r < rows.length; r++) {
			cols = rows[r].split(StringUtil.COLONORSPACES);
			for (int c = 0; c < cols.length; c++) {
				values[r][c] = cols[c];
			}
		}
	}

	public DefaultDenseStringMatrix2D(Matrix source) throws MatrixException {
		this(source.getSize());
		for (long[] c : source.availableCoordinates()) {
			setString(source.getString(c), c);
		}
	}

	public DefaultDenseStringMatrix2D(long... size) {
		values = new String[(int) size[ROW]][(int) size[COLUMN]];
	}

	public DefaultDenseStringMatrix2D(String[]... values) {
		this.values = values;
	}

	public long[] getSize() {
		return new long[] { values.length, values[0].length };
	}

	@Override
	public long getRowCount() {
		return values.length;
	}

	@Override
	public long getColumnCount() {
		return values[0].length;
	}

	public String getString(long... coordinates) {
		return values[(int) coordinates[ROW]][(int) coordinates[COLUMN]];
	}

	public void setString(String value, long... coordinates) {
		values[(int) coordinates[ROW]][(int) coordinates[COLUMN]] = value;
	}

	public Object getMatrixAnnotation() {
		return annotation == null ? null : annotation.getMatrixAnnotation();
	}

	public void setMatrixAnnotation(Object value) {
		if (annotation == null) {
			annotation = new DefaultAnnotation();
		}
		annotation.setMatrixAnnotation(value);
	}

	public Object getAxisAnnotation(int axis, int positionOnAxis) {
		return annotation == null ? null : annotation.getAxisAnnotation(axis, positionOnAxis);
	}

	public void setAxisAnnotation(int axis, int positionOnAxis, Object value) {
		if (annotation == null) {
			annotation = new DefaultAnnotation();
		}
		annotation.setAxisAnnotation(axis, positionOnAxis, value);
	}

	public Object getAxisAnnotation(int axis) {
		return annotation == null ? null : annotation.getAxisAnnotation(axis);
	}

	public void setAxisAnnotation(int axis, Object value) {
		if (annotation == null) {
			annotation = new DefaultAnnotation();
		}
		annotation.setAxisAnnotation(axis, value);
	}

}
