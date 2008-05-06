package org.jdmp.matrix.calculation;

import org.jdmp.matrix.Coordinates;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.Matrix.EntryType;

public abstract class DoubleCalculation extends AbstractCalculation {

	public DoubleCalculation(Matrix... sources) {
		super(sources);
	}

	public DoubleCalculation(int dimension, Matrix... sources) {
		super(dimension, sources);
	}

	public Object getObject(long... coordinates) throws MatrixException {
		return getDouble(coordinates);
	}

	public String getString(long... coordinates) throws MatrixException {
		return "" + getDouble(coordinates);
	}

	public void setObject(Object object, long... coordinates) throws MatrixException {
	}

	public void setDouble(double value, long... coordinates) throws MatrixException {
	}

	public void setString(String value, long... coordinates) throws MatrixException {
	}

	public final Matrix calcNew() throws MatrixException {
		Matrix result = MatrixFactory.zeros(getEntryType(), getSize());
		// TODO: copy annotation
		for (long[] c : result.allCoordinates()) {
			result.setDouble(getDouble(c), c);
		}
		return result;
	}

	public final Matrix calcOrig() throws MatrixException {
		if (!Coordinates.equals(getSource().getSize(), getSize())) {
			throw new MatrixException("Cannot change Matrix size. Use calc(Ret.NEW) or calc(Ret.LINK) instead.");
		}
		for (long[] c : getSource().allCoordinates()) {
			getSource().setDouble(getDouble(c), c);
		}
		return getSource();
	}

	public EntryType getEntryType() {
		return EntryType.DOUBLE;
	}

}
