package org.jdmp.matrix.calculation;

import org.jdmp.matrix.Coordinates;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.Matrix.EntryType;
import org.jdmp.matrix.util.MathUtil;
import org.jdmp.matrix.util.StringUtil;

public abstract class ObjectCalculation extends AbstractCalculation {

	public ObjectCalculation(Matrix... sources) {
		super(sources);
	}

	public ObjectCalculation(int dimension, Matrix... sources) {
		super(dimension, sources);
	}

	public double getDouble(long... coordinates) throws MatrixException {
		return MathUtil.getDouble(getObject(coordinates));
	}

	public void setDouble(double value, long... coordinates) throws MatrixException {
		setObject(value, coordinates);
	}

	public void setObject(Object value, long... coordinates) throws MatrixException {
	}

	public void setString(String value, long... coordinates) throws MatrixException {
		setObject(value, coordinates);
	}

	public String getString(long... coordinates) throws MatrixException {
		return StringUtil.format(getObject(coordinates));
	}

	public final Matrix calcNew() throws MatrixException {
		Matrix result = MatrixFactory.zeros(getEntryType(), getSize());
		// TODO: copy annotation

		switch (getEntryType()) {
		case DOUBLE:
			for (long[] c : result.allCoordinates()) {
				result.setDouble(getDouble(c), c);
			}
			break;
		default:
			for (long[] c : result.allCoordinates()) {
				result.setObject(getObject(c), c);
			}
			break;
		}
		return result;
	}

	public final Matrix calcOrig() throws MatrixException {
		if (!Coordinates.equals(getSource().getSize(), getSize())) {
			throw new MatrixException("Cannot change Matrix size. Use calc(Ret.NEW) or calc(Ret.LINK) instead.");
		}
		for (long[] c : getSource().allCoordinates()) {
			getSource().setObject(getObject(c), c);
		}
		return getSource();
	}

	public EntryType getEntryType() {
		return getSource().getEntryType();
	}

}
