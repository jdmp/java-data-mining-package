package org.jdmp.matrix.calculation.basic;

import java.util.List;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.ObjectCalculation;
import org.jdmp.matrix.util.MathUtil;
import org.jdmp.matrix.util.StringUtil;

public class Deletion extends ObjectCalculation {
	private static final long serialVersionUID = 3714270132906708701L;

	private long[][] selection = null;

	public Deletion(Matrix m, String deletionString) {
		this(m, StringUtil.parseSelection(deletionString, m.getSize()));
	}

	public Deletion(Matrix m, List<? extends Number>... deletion) {
		super(m);

		List<Long> rows = MathUtil.sequenceListLong(0, getSource().getRowCount() - 1);
		List<Long> columns = MathUtil.sequenceListLong(0, getSource().getColumnCount() - 1);

		for (int r = 0; r < deletion[ROW].size(); r++) {
			rows.remove((Long) deletion[ROW].get(r).longValue());
		}

		for (int c = 0; c < deletion[COLUMN].size(); c++) {
			columns.remove((Long) deletion[COLUMN].get(c).longValue());
		}

		selection = new long[2][];
		selection[ROW] = MathUtil.listToLong(rows);
		selection[COLUMN] = MathUtil.listToLong(columns);
	}

	public Deletion(Matrix m, long[]... deletion) {
		super(m);

		List<Long> rows = MathUtil.sequenceListLong(0, getSource().getRowCount() - 1);
		List<Long> columns = MathUtil.sequenceListLong(0, getSource().getColumnCount() - 1);

		for (int r = 0; r < deletion[ROW].length; r++) {
			rows.remove(deletion[ROW][r]);
		}

		for (int c = 0; c < deletion[COLUMN].length; c++) {
			columns.remove(deletion[COLUMN][c]);
		}

		selection = new long[2][];
		selection[ROW] = MathUtil.listToLong(rows);
		selection[COLUMN] = MathUtil.listToLong(columns);
	}

	public Object getObject(long... coordinates) throws MatrixException {
		return getSource().getObject(selection[ROW][(int) coordinates[ROW]],
				selection[COLUMN][(int) coordinates[COLUMN]]);
	}

	public void setObject(Object o, long... coordinates) throws MatrixException {
		getSource().setObject(o, selection[ROW][(int) coordinates[ROW]], selection[COLUMN][(int) coordinates[COLUMN]]);
	}

	public double getDouble(long... coordinates) throws MatrixException {
		return getSource().getDouble(selection[ROW][(int) coordinates[ROW]],
				selection[COLUMN][(int) coordinates[COLUMN]]);
	}

	public void setDouble(double v, long... coordinates) throws MatrixException {
		getSource().setDouble(v, selection[ROW][(int) coordinates[ROW]], selection[COLUMN][(int) coordinates[COLUMN]]);
	}

	public long[] getSize() {
		return new long[] { selection[ROW].length, selection[COLUMN].length };
	}

}
