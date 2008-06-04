package org.jdmp.jmatio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.interfaces.Wrapper;
import org.jdmp.matrix.stubs.AbstractDenseDoubleMatrix2D;

import com.jmatio.types.MLDouble;

public class MLDoubleMatrix extends AbstractDenseDoubleMatrix2D implements Wrapper<MLDouble> {
	private static final long serialVersionUID = 5687213209146399315L;

	private transient MLDouble matrix = null;

	public MLDoubleMatrix(Matrix m) {
		this(m.getSize());
		if (m.getLabel() != null) {
			setLabel(m.getLabel());
		}
		for (long[] c : m.availableCoordinates()) {
			setDouble(m.getDouble(c), c);
		}
	}

	public MLDoubleMatrix(long... size) {
		this.matrix = new MLDouble("matrix" + System.nanoTime(), new double[(int) size[ROW]][(int) size[COLUMN]]);
		setAnnotation(new MLAnnotation(matrix));
	}

	public MLDoubleMatrix(MLDouble matrix) {
		this.matrix = matrix;
		setAnnotation(new MLAnnotation(matrix));
	}

	public long[] getSize() {
		return new long[] { matrix.getM(), matrix.getN() };
	}

	public double getDouble(long... coordinates) {
		return matrix.get((int) coordinates[ROW], (int) coordinates[COLUMN]);
	}

	public void setDouble(double value, long... coordinates) {
		matrix.set(value, (int) coordinates[ROW], (int) coordinates[COLUMN]);
	}

	public MLDouble getWrappedObject() {
		return matrix;
	}

	public void setWrappedObject(MLDouble object) {
		this.matrix = object;
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		String name = (String) s.readObject();
		double[][] values = (double[][]) s.readObject();
		matrix = new MLDouble(name, values);
		((MLAnnotation) getAnnotation()).setMLArray(matrix);
	}

	private void writeObject(ObjectOutputStream s) throws IOException, MatrixException {
		s.defaultWriteObject();
		s.writeObject(matrix.name);
		s.writeObject(this.toDoubleArray());
	}

}
