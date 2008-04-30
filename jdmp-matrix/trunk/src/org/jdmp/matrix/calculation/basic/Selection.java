package org.jdmp.matrix.calculation.basic;

import java.util.List;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.ObjectCalculation;
import org.jdmp.matrix.util.MathUtil;
import org.jdmp.matrix.util.StringUtil;

public class Selection extends ObjectCalculation {
	private static final long serialVersionUID = 4576183558391811345L;

	private long[][] selection = null;

	public Selection(Matrix m, String selectionString) {
		super(m);
		selection = StringUtil.parseSelection(selectionString, m.getSize());
	}

	public Selection(Matrix m, List<? extends Number>... selection) {
		super(m);
		this.selection = new long[selection.length][];
		this.selection[ROW] = MathUtil.listToLong(selection[ROW]);
		this.selection[COLUMN] = MathUtil.listToLong(selection[COLUMN]);
	}

	public Selection(Matrix m, long[]... selection) {
		super(m);
		this.selection = selection;
	}

	public Object getObject(long... coordinates) throws MatrixException {
		return getSource().getObject(selection[ROW][(int) coordinates[ROW]],
				selection[COLUMN][(int) coordinates[COLUMN]]);
	}

	public void setObject(Object o, long... coordinates) throws MatrixException {
		getSource().setObject(o, selection[ROW][(int) coordinates[ROW]], selection[COLUMN][(int) coordinates[COLUMN]]);
	}

	public long[] getSize() {
		return new long[] { selection[ROW].length, selection[COLUMN].length };
	}

}
