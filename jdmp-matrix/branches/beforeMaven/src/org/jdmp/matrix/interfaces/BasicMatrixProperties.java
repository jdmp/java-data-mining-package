package org.jdmp.matrix.interfaces;

import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.Matrix.EntryType;

public interface BasicMatrixProperties {

	public EntryType getEntryType();

	public long getValueCount();

	public boolean isReadOnly();

	public boolean equals(Object o);

	public boolean equalsContent(Object o);

	public boolean equalsAnnotation(Object o);

	public int rank() throws MatrixException;

	public double trace() throws MatrixException;

	public boolean isDiagonal() throws MatrixException;

	public boolean isSquare();

	public boolean isSymmetric();

	public boolean isEmpty() throws MatrixException;

	public boolean isColumnVector();

	public boolean isRowVector();

	public boolean isScalar();

	public boolean isResizeable();

	public boolean isMultidimensionalMatrix();

	public boolean isSparse();

	public boolean isTransient();

	public boolean containsMissingValues() throws MatrixException;

	public double getDoubleValue() throws MatrixException;

	public double getMinValue() throws MatrixException;

	public double getMeanValue() throws MatrixException;

	public double getStdValue() throws MatrixException;

	public double getMaxValue() throws MatrixException;

	public double getEuklideanValue() throws MatrixException;

	public double getValueSum() throws MatrixException;

	public double getAbsoluteValueSum() throws MatrixException;

	public double getAbsoluteValueMean() throws MatrixException;

	public double getRMS() throws MatrixException;

	public long getRowCount();

	public long getColumnCount();

	public long getZCount();

	public long getSize(int dimension);

	public long[] getSize();

	/**
	 * Sets the size of the matrix. This is an optional method that is not
	 * implemented for all matrizes. If this method is not implemented, a
	 * <code>MatrixException</code> is thrown.
	 * 
	 * @param size
	 *            the new size of the matrix
	 */
	public void setSize(long... size);

	public int getDimensionCount();

	public String toString();

}
