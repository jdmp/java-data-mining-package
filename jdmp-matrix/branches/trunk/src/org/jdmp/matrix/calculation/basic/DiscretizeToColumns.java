package org.jdmp.matrix.calculation.basic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.ObjectCalculation;
import org.jdmp.matrix.util.MathUtil;

public class DiscretizeToColumns extends ObjectCalculation {
	private static final long serialVersionUID = -3606534079672701424L;

	private long column = 0;

	private List<Object> values = null;

	private boolean ignoreNaN = false;

	public DiscretizeToColumns(Matrix matrix, boolean ignoreNaN, long column) {
		super(matrix);
		this.column = column;
		this.ignoreNaN = ignoreNaN;
	}

	public long[] getSize() {
		try {
			countValues();
		} catch (MatrixException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long[] size = getSource().getSize();
		size[COLUMN] += values.size() - 1;
		return size;
	}

	public Object getObject(long... coordinates) throws MatrixException {
		countValues();
		if (coordinates[COLUMN] < column) {
			return getSource().getObject(coordinates);
		} else if (coordinates[COLUMN] >= column + values.size()) {
			long col = coordinates[COLUMN] - values.size() + 1;
			return getSource().getObject(coordinates[ROW], col);
		} else {
			Object o = getSource().getObject(coordinates[ROW], column);
			if (ignoreNaN) {
				if (MathUtil.isNaNOrInfinite(o)) {
					return 0.0;
				} else {
					int index = values.indexOf(o);
					long col = coordinates[COLUMN] - column;
					if (index == col) {
						return 1.0;
					} else {
						return 0.0;
					}
				}
			} else {
				if (MathUtil.isNaNOrInfinite(o)) {
					return Double.NaN;
				} else {
					int index = values.indexOf(o);
					long col = coordinates[COLUMN] - column;
					if (index == col) {
						return 1.0;
					} else {
						return 0.0;
					}
				}
			}

		}
	}

	private void countValues() throws MatrixException {
		if (values == null) {
			Set<Object> set = new HashSet<Object>();
			for (long row = getSource().getRowCount(); --row >= 0;) {
				Object o = getSource().getObject(row, column);
				if (ignoreNaN) {
					if (MathUtil.isNaNOrInfinite(o)) {
						set.add(Double.valueOf(0.0));
					} else {
						set.add(o);
					}
				} else {
					if (!MathUtil.isNaNOrInfinite(o)) {
						set.add(o);
					}
				}

			}
			values = new ArrayList<Object>(set);
		}
	}

}
