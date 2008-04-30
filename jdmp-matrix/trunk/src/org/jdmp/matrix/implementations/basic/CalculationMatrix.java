package org.jdmp.matrix.implementations.basic;

import org.jdmp.matrix.GenericMatrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.AbstractCalculation;

public class CalculationMatrix extends GenericMatrix {
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
	public Object getObject(long... coordinates) throws MatrixException {
		return calculation.getObject(coordinates);
	}

	public void setObject(Object o, long... coordinates) throws MatrixException {
		calculation.setObject(o, coordinates);
	}

	public boolean isSparse() {
		return calculation.isSparse();
	}

}
