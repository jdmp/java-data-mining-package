package org.jdmp.core.variable;

import org.jdmp.core.CoreObject;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.interfaces.HasDescription;
import org.jdmp.matrix.interfaces.HasLabel;
import org.jdmp.matrix.interfaces.HasMatrix;
import org.jdmp.matrix.interfaces.HasMatrixList;

public interface Variable extends CoreObject, HasLabel, HasDescription, HasMatrix, HasMatrixList {

	public long[] getSize();

	public void setSize(long... size);

	public int getMemorySize();

	public double getValue() throws MatrixException;

	public void setValue(double value);

	public Matrix getAsMatrix();

	public void addMatrix(Matrix m);

	public void removeVariableListener(VariableListener l);

	public void fireVariableEvent(VariableEvent e);

	public void setMatrix(int index, Matrix m);

	public void addVariableListener(VariableListener l);

	public int getIndexOfMatrix(Matrix m);

	public void clear();

	public long getRowCount();

	public long getColumnCount();

	public double getMinValue() throws MatrixException;

	public double getMaxValue() throws MatrixException;

	public long getIndexOfMaximum() throws MatrixException;

	public long getIndexOfMinimum() throws MatrixException;

	public Matrix getMeanMatrix() throws MatrixException;

	public Matrix getMaxMatrix() throws MatrixException;

	public Matrix getMinMatrix() throws MatrixException;

	public Matrix getVarianceMatrix() throws MatrixException;

	public Matrix getStandardDeviationMatrix() throws MatrixException;
}
