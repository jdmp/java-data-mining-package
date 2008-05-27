package org.jdmp.matrix.implementations.basic;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.stubs.AbstractDenseObjectMatrix2D;

public class DefaultDenseObjectMatrix2D extends AbstractDenseObjectMatrix2D {
	private static final long serialVersionUID = 7405594663844848420L;

	private Object[][] values = null;

	public DefaultDenseObjectMatrix2D(Object[]... values) {
		this.values = values;
	}

	public DefaultDenseObjectMatrix2D(long... size) {
		values = new Object[(int) size[ROW]][(int) size[COLUMN]];
	}

	public DefaultDenseObjectMatrix2D(Matrix m) throws MatrixException {
		if (m instanceof DefaultDenseObjectMatrix2D) {
			Object[][] v = ((DefaultDenseObjectMatrix2D) m).values;
			this.values = new Object[v.length][v[0].length];
			for (int r = v.length; --r >= 0;) {
				for (int c = v[0].length; --c >= 0;) {
					values[r][c] = v[r][c];
				}
			}
		} else {
			values = new Object[(int) m.getRowCount()][(int) m.getColumnCount()];
			for (long[] c : m.allCoordinates()) {
				setObject(m.getObject(c), c);
			}
		}
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

	public Object getObject(long... coordinates) {
		return values[(int) coordinates[ROW]][(int) coordinates[COLUMN]];
	}

	public void setObject(Object value, long... coordinates) {
		values[(int) coordinates[ROW]][(int) coordinates[COLUMN]] = value;
	}
	
}
