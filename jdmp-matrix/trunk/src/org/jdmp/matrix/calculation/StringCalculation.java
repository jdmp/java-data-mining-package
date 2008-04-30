package org.jdmp.matrix.calculation;

import org.jdmp.matrix.Coordinates;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.Matrix.EntryType;
import org.jdmp.matrix.util.MathUtil;

public abstract class StringCalculation extends AbstractCalculation {

	public StringCalculation(Matrix... sources) {
		super(sources);
	}

	public StringCalculation(int dimension, Matrix... sources) {
		super(dimension, sources);
	}

	public double getDouble(long... coordinates) throws MatrixException {
		return MathUtil.getDouble(getString(coordinates));
	}

	public Object getObject(long... coordinates) throws MatrixException {
		return getString(coordinates);
	}

	public void setDouble(double value, long... coordinates) throws MatrixException {
	}

	public void setObject(Object value, long... coordinates) throws MatrixException {
	}

	public void setString(String value, long... coordinates) throws MatrixException {
	}

	public final Matrix calcOrig() throws MatrixException {
		if (!Coordinates.equals(getSource().getSize(), getSize())) {
			throw new MatrixException("Cannot change Matrix size. Use calc(Ret.NEW) or calc(Ret.LINK) instead.");
		}
		for (long[] c : getSource().allCoordinates()) {
			getSource().setString(getString(c), c);
		}
		return getSource();
	}

	public final Matrix calcNew() throws MatrixException {
		Matrix result = Matrix.zeros(getEntryType(), getSize());
		// TODO: copy annotation
		for (long[] c : result.allCoordinates()) {
			result.setString(getString(c), c);
		}
		return result;
	}

	public EntryType getEntryType() {
		return EntryType.STRING;
	}

}
