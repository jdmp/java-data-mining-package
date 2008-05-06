package org.jdmp.matrix.calculation.entrywise.creators;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.Matrix.EntryType;
import org.jdmp.matrix.calculation.ObjectCalculation;

public class Fill extends ObjectCalculation {
	private static final long serialVersionUID = -3477957135967841340L;

	private Object fill = null;

	public Fill(Matrix matrix, Object value) {
		super(matrix);
		this.fill = value;
	}

	@Override
	public Object getObject(long... coordinates) {
		return fill;
	}

	public static Matrix calc(Matrix source, Object fill) throws MatrixException {
		Matrix ret = MatrixFactory.zeros(source.getEntryType(), source.getSize());
		for (long[] c : source.allCoordinates()) {
			ret.setObject(fill, c);
		}
		return ret;
	}

	public static Matrix calc(Object fill, long... size) throws MatrixException {
		Matrix ret = null;
		if (fill instanceof Number) {
			ret = MatrixFactory.zeros(EntryType.DOUBLE, size);
		} else if (fill instanceof String) {
			ret = MatrixFactory.zeros(EntryType.STRING, size);
		} else {
			ret = MatrixFactory.zeros(EntryType.OBJECT, size);
		}
		for (long[] c : ret.allCoordinates()) {
			ret.setObject(fill, c);
		}
		return ret;
	}
}
