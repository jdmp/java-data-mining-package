package org.jdmp.core.variable;

import java.io.InputStream;

import javax.swing.Icon;

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

	public void loadTSP(InputStream is);

	public void loadTSP(String file);

	public int getMemorySize();

	public Matrix getMatrixCopy();

	public Matrix getMatrixCopy(int index);

	public void clearHistory();

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

	public void fillGaussian() throws MatrixException;

	public void fillUniform() throws MatrixException;

	public void divideBy(double v) throws MatrixException;

	public void plus(double v) throws MatrixException;

	public void fillWithValue(double v) throws MatrixException;

	public void setMemorySize(int size);

	public void convertIntToVector(int numberOfClasses) throws MatrixException;

	public long getRowCount();

	public long getColumnCount();

	public double getEuklideanValue() throws MatrixException;

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
