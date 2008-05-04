package org.jdmp.matrix.implementations.basic;

import org.jdmp.matrix.AbstractGenericMatrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.AbstractCalculation;

public class CalculationMatrix<A> extends AbstractGenericMatrix<A> {
	private static final long serialVersionUID = -8345796002435936888L;

	private AbstractCalculation calculation = null;

	public CalculationMatrix(AbstractCalculation calculation) {
		this.calculation = calculation;
	}

	public EntryType getEntryType() {
		return calculation.getEntryType();
	}

	public boolean contains(long... coordinates) {
		return calculation.contains(coordinates);
	}

	public Iterable<long[]> allCoordinates() {
		return calculation.allCoordinates();
	}

	public Iterable<long[]> availableCoordinates() {
		return calculation.availableCoordinates();
	}

	public long[] getSize() {
		return calculation.getSize();
	}

	public double getDouble(long... coordinates) throws MatrixException {
		return calculation.getDouble(coordinates);
	}

	public void setDouble(double value, long... coordinates) throws MatrixException {
		calculation.setDouble(value, coordinates);
	}

	@Override
	public A getObject(long... coordinates) throws MatrixException {
		return (A) calculation.getObject(coordinates);
	}

	public void setObject(Object o, long... coordinates) throws MatrixException {
		calculation.setObject(o, coordinates);
	}

	@Override
	public String getString(long... coordinates) throws MatrixException {
		return calculation.getString(coordinates);
	}

	public void setString(String s, long... coordinates) throws MatrixException {
		calculation.setString(s, coordinates);
	}

	public boolean isSparse() {
		return calculation.isSparse();
	}

}
