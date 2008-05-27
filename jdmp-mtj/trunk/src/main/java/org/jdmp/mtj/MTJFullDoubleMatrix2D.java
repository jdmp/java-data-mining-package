package org.jdmp.mtj;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import no.uib.cipr.matrix.DenseMatrix;
import no.uib.cipr.matrix.Matrices;
import no.uib.cipr.matrix.SVD;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.calculation.Calculation.Ret;
import org.jdmp.matrix.interfaces.Wrapper;
import org.jdmp.matrix.stubs.AbstractDenseDoubleMatrix2D;

public class MTJFullDoubleMatrix2D extends AbstractDenseDoubleMatrix2D implements Wrapper<DenseMatrix> {
	private static final long serialVersionUID = -2386081646062313108L;

	private transient DenseMatrix matrix = null;

	public MTJFullDoubleMatrix2D(DenseMatrix m) {
		this.matrix = m;
	}

	public MTJFullDoubleMatrix2D(Matrix m) throws MatrixException {
		if (m instanceof MTJFullDoubleMatrix2D) {
			this.matrix = ((MTJFullDoubleMatrix2D) m).matrix.copy();
		} else {
			this.matrix = new DenseMatrix(m.toDoubleArray());
		}
	}

	public MTJFullDoubleMatrix2D(long... size) {
		this.matrix = new DenseMatrix((int) size[ROW], (int) size[COLUMN]);
	}

	public Matrix inv() {
		DenseMatrix A = (DenseMatrix) this.getWrappedObject();
		DenseMatrix I = Matrices.identity((int) getColumnCount());
		DenseMatrix AI = I.copy();
		return new MTJFullDoubleMatrix2D((DenseMatrix) A.solve(I, AI));
	}

	public Matrix[] svd() throws MatrixException {
		try {
			SVD svd = SVD.factorize(getWrappedObject());
			Matrix u = new MTJFullDoubleMatrix2D(svd.getU());
			Matrix v = new MTJFullDoubleMatrix2D(svd.getVt()).transpose();
			double[] svs = svd.getS();
			Matrix s = MatrixFactory.zeros(getSize());
			for (int i = (int) Math.min(s.getRowCount(), s.getColumnCount()); --i >= 0;) {
				s.setDouble(svs[i], i, i);
			}

			return new Matrix[] { u, s, v };
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	public double getDouble(long... coordinates) {
		return matrix.getData()[(int) (coordinates[ROW] + coordinates[COLUMN] * matrix.numRows())];
	}

	public long[] getSize() {
		return new long[] { matrix.numRows(), matrix.numColumns() };
	}

	public void setDouble(double value, long... coordinates) {
		matrix.getData()[(int) (coordinates[ROW] + coordinates[COLUMN] * matrix.numRows())] = value;
	}

	@Override
	public Matrix transpose() {
		// TODO: maybe this can be made faster using System.arraycopy()
		DenseMatrix ret = new DenseMatrix((int) getColumnCount(), (int) getRowCount());
		return new MTJFullDoubleMatrix2D((DenseMatrix) matrix.transpose(ret));
	}

	public DenseMatrix getWrappedObject() {
		return matrix;
	}

	public void setWrappedObject(DenseMatrix object) {
		this.matrix = object;
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		double[][] values = (double[][]) s.readObject();
		matrix = new DenseMatrix(values);
	}

	private void writeObject(ObjectOutputStream s) throws IOException, MatrixException {
		s.defaultWriteObject();
		s.writeObject(this.toDoubleArray());
	}

	public Matrix mtimes(Matrix m2) throws MatrixException {
		if (m2 instanceof MTJFullDoubleMatrix2D) {
			DenseMatrix a = matrix;
			DenseMatrix b = ((MTJFullDoubleMatrix2D) m2).getWrappedObject();
			DenseMatrix c = new DenseMatrix(a.numRows(), b.numColumns());
			a.mult(b, c);
			return new MTJFullDoubleMatrix2D(c);
		} else {
			return super.mtimes(m2);
		}
	}

	public Matrix plus(Matrix m2) throws MatrixException {
		if (m2 instanceof MTJFullDoubleMatrix2D) {
			DenseMatrix a = new DenseMatrix(matrix.numRows(), matrix.numColumns());
			System.arraycopy(matrix.getData(), 0, a.getData(), 0, matrix.getData().length);
			DenseMatrix b = ((MTJFullDoubleMatrix2D) m2).getWrappedObject();
			for (int i = a.getData().length; --i >= 0;) {
				a.getData()[i] += b.getData()[i];
			}
			return new MTJFullDoubleMatrix2D(a);
		} else {
			return super.plus(m2);
		}
	}

	public Matrix times(Matrix m2) throws MatrixException {
		if (m2 instanceof MTJFullDoubleMatrix2D) {
			DenseMatrix a = new DenseMatrix(matrix.numRows(), matrix.numColumns());
			System.arraycopy(matrix.getData(), 0, a.getData(), 0, matrix.getData().length);
			DenseMatrix b = ((MTJFullDoubleMatrix2D) m2).getWrappedObject();
			for (int i = a.getData().length; --i >= 0;) {
				a.getData()[i] *= b.getData()[i];
			}
			return new MTJFullDoubleMatrix2D(a);
		} else {
			return super.plus(m2);
		}
	}

	public Matrix minus(Matrix m2) throws MatrixException {
		if (m2 instanceof MTJFullDoubleMatrix2D) {
			DenseMatrix a = new DenseMatrix(matrix.numRows(), matrix.numColumns());
			System.arraycopy(matrix.getData(), 0, a.getData(), 0, matrix.getData().length);
			DenseMatrix b = ((MTJFullDoubleMatrix2D) m2).getWrappedObject();
			for (int i = a.getData().length; --i >= 0;) {
				a.getData()[i] -= b.getData()[i];
			}
			return new MTJFullDoubleMatrix2D(a);
		} else {
			return super.minus(m2);
		}
	}

	public Matrix plus(Ret returnType, boolean ignoreNaN, double value) throws MatrixException {
		if (ignoreNaN) {
			switch (returnType) {
			case ORIG:
				return super.plus(returnType, ignoreNaN, value);
			case LINK:
				return super.plus(returnType, ignoreNaN, value);
			default:
				return super.plus(returnType, ignoreNaN, value);
			}
		} else {
			switch (returnType) {
			case ORIG:
				return super.plus(returnType, ignoreNaN, value);
			case LINK:
				return super.plus(returnType, ignoreNaN, value);
			default:
				return super.plus(returnType, ignoreNaN, value);
			}
		}
	}

	public Matrix minus(Ret returnType, boolean ignoreNaN, double value) throws MatrixException {
		if (ignoreNaN) {
			switch (returnType) {
			case ORIG:
				return super.minus(returnType, ignoreNaN, value);
			case LINK:
				return super.minus(returnType, ignoreNaN, value);
			default:
				return super.minus(returnType, ignoreNaN, value);
			}
		} else {
			switch (returnType) {
			case ORIG:
				return super.minus(returnType, ignoreNaN, value);
			case LINK:
				return super.minus(returnType, ignoreNaN, value);
			default:
				return super.minus(returnType, ignoreNaN, value);
			}
		}
	}

	public Matrix minus(Ret returnType, boolean ignoreNaN, Matrix m2) throws MatrixException {
		if (m2 instanceof MTJFullDoubleMatrix2D) {
			DenseMatrix b = ((MTJFullDoubleMatrix2D) m2).getWrappedObject();

			if (ignoreNaN) {
				switch (returnType) {
				case ORIG:
					return super.minus(returnType, ignoreNaN, m2);
				case LINK:
					return super.minus(returnType, ignoreNaN, m2);
				default:
					return super.minus(returnType, ignoreNaN, m2);
				}
			} else {
				switch (returnType) {
				case ORIG:
					for (int i = matrix.getData().length; --i >= 0;) {
						matrix.getData()[i] -= b.getData()[i];
					}
				case LINK:
					return super.minus(returnType, ignoreNaN, m2);
				default:
					return super.minus(returnType, ignoreNaN, m2);
				}
			}
		} else {
			return super.minus(returnType, ignoreNaN, m2);
		}
	}

	public Matrix plus(Ret returnType, boolean ignoreNaN, Matrix m2) throws MatrixException {
		if (ignoreNaN) {
			switch (returnType) {
			case ORIG:
				return super.plus(returnType, ignoreNaN, m2);
			case LINK:
				return super.plus(returnType, ignoreNaN, m2);
			default:
				return super.plus(returnType, ignoreNaN, m2);
			}
		} else {
			switch (returnType) {
			case ORIG:
				return super.plus(returnType, ignoreNaN, m2);
			case LINK:
				return super.plus(returnType, ignoreNaN, m2);
			default:
				return super.plus(returnType, ignoreNaN, m2);
			}
		}
	}

	public Matrix minus(double f) throws MatrixException {
		DenseMatrix a = new DenseMatrix(matrix.numRows(), matrix.numColumns());
		System.arraycopy(matrix.getData(), 0, a.getData(), 0, matrix.getData().length);
		for (int i = a.getData().length; --i >= 0;) {
			a.getData()[i] -= f;
		}
		return new MTJFullDoubleMatrix2D(a);
	}

	public Matrix plus(double f) throws MatrixException {
		DenseMatrix a = new DenseMatrix(matrix.numRows(), matrix.numColumns());
		System.arraycopy(matrix.getData(), 0, a.getData(), 0, matrix.getData().length);
		for (int i = a.getData().length; --i >= 0;) {
			a.getData()[i] += f;
		}
		return new MTJFullDoubleMatrix2D(a);
	}

	public Matrix times(double f) throws MatrixException {
		DenseMatrix a = new DenseMatrix(matrix.numRows(), matrix.numColumns());
		System.arraycopy(matrix.getData(), 0, a.getData(), 0, matrix.getData().length);
		for (int i = a.getData().length; --i >= 0;) {
			a.getData()[i] *= f;
		}
		return new MTJFullDoubleMatrix2D(a);
	}

	public Matrix divide(double f) throws MatrixException {
		DenseMatrix a = new DenseMatrix(matrix.numRows(), matrix.numColumns());
		System.arraycopy(matrix.getData(), 0, a.getData(), 0, matrix.getData().length);
		for (int i = a.getData().length; --i >= 0;) {
			a.getData()[i] /= f;
		}
		return new MTJFullDoubleMatrix2D(a);
	}

	public Matrix copy() {
		return new MTJFullDoubleMatrix2D(matrix.copy());
	}

	public boolean containsNaN() {
		double[] data = matrix.getData();
		for (int i = matrix.getData().length; --i >= 0;) {
			if (Double.isNaN(data[i])) {
				return true;
			}
		}
		return false;
	}

}
