/*
 * MatrixFactory.java
 *
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
 *
 * This file is part of jdmp.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership and licensing.
 *
 * jdmp is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * jdmp is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with jdmp; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.jdmp.matrix;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdmp.matrix.Matrix.AnnotationTransfer;
import org.jdmp.matrix.Matrix.EntryType;
import org.jdmp.matrix.Matrix.Format;
import org.jdmp.matrix.calculation.basic.Convert;
import org.jdmp.matrix.calculation.entrywise.creators.Eye;
import org.jdmp.matrix.calculation.entrywise.creators.Fill;
import org.jdmp.matrix.calculation.entrywise.creators.Ones;
import org.jdmp.matrix.calculation.entrywise.creators.Rand;
import org.jdmp.matrix.calculation.entrywise.creators.Randn;
import org.jdmp.matrix.implementations.basic.DefaultDenseDoubleMatrix2D;
import org.jdmp.matrix.implementations.basic.DefaultDenseObjectMatrix2D;
import org.jdmp.matrix.implementations.basic.DefaultDenseStringMatrix2D;
import org.jdmp.matrix.implementations.basic.SynchronizedMatrix;
import org.jdmp.matrix.implementations.collections.DefaultListMatrix;
import org.jdmp.matrix.implementations.collections.DefaultMapMatrix;
import org.jdmp.matrix.implementations.io.DenseFileMatrix2D;
import org.jdmp.matrix.implementations.io.FileListMatrix;
import org.jdmp.matrix.implementations.misc.BufferedObjectMatrix;
import org.jdmp.matrix.io.Import;
import org.jdmp.matrix.stubs.AbstractGenericMatrix;
import org.jdmp.matrix.util.MathUtil;

/**
 * This class provides a factory for matrix generation.
 * 
 * 
 * 
 * @author Andreas Naegele
 */
public abstract class MatrixFactory {

	protected transient static final Logger logger = Logger.getLogger(MatrixFactory.class.getName());

	public static final int ROW = Matrix.ROW;

	public static final int COLUMN = Matrix.COLUMN;

	public static final int Y = Matrix.Y;

	public static final int X = Matrix.X;

	public static final int Z = Matrix.Z;

	public static final int ALL = Matrix.ALL;

	public static final int NONE = Matrix.NONE;

	public static final <A>Matrix<A> horCat(Matrix<A>... matrices) throws MatrixException {
		return concat(COLUMN, matrices);
	}

	public static final <A>Matrix<A> vertCat(Matrix<A>... matrices) throws MatrixException {
		return concat(ROW, matrices);
	}

	public static final <A>Matrix<A> vertCat(Collection<Matrix> matrices) throws MatrixException {
		return concat(ROW, matrices);
	}

	public static final <A>Matrix<A> horCat(Collection<Matrix> matrices) throws MatrixException {
		return concat(COLUMN, matrices);
	}

	public static final <A>Matrix<A> concat(int dimension, Matrix<A>... matrices) throws MatrixException {
		Matrix<A> result = MatrixFactory.copyOf(AnnotationTransfer.COPY, matrices[0]);
		for (int i = 1; i < matrices.length; i++) {
			result = result.append(dimension, matrices[i]);
		}
		return result;
	}

	public static final <A>Matrix<A> concat(int dimension, Collection<Matrix> matrices) throws MatrixException {
		List<Matrix> list = new ArrayList<Matrix>(matrices);
		Matrix result = MatrixFactory.copyOf(AnnotationTransfer.COPY, list.get(0));
		for (int i = 1; i < matrices.size(); i++) {
			result = result.append(dimension, list.get(i));
		}
		return result;
	}

	public static final <A>Matrix<A> copyFromArray(double[]... values) {
		int rows = values.length;
		int columns = 0;
		for (int i = values.length - 1; i >= 0; i--) {
			columns = Math.max(columns, values[i].length);
		}
		Matrix m = MatrixFactory.zeros(rows, columns);
		for (int i = rows - 1; i >= 0; i--) {
			for (int j = values[i].length - 1; j >= 0; j--) {
				m.setDouble(values[i][j], i, j);
			}
		}
		return m;
	}

	public static final DefaultDenseDoubleMatrix2D fromArray(double[]... values) {
		return new DefaultDenseDoubleMatrix2D(values);
	}

	public static final DefaultDenseDoubleMatrix2D fromArray(double... values) {
		return new DefaultDenseDoubleMatrix2D(values);
	}

	public static final DefaultDenseDoubleMatrix2D fromArray(int[]... values) {
		double[][] doubleValues = MathUtil.toDoubleArray(values);
		return new DefaultDenseDoubleMatrix2D(doubleValues);
	}

	public static final DefaultDenseDoubleMatrix2D fromArray(int... values) {
		double[] doubleValues = MathUtil.toDoubleArray(values);
		return new DefaultDenseDoubleMatrix2D(doubleValues);
	}

	public static final <A>Matrix<A> copyOf(AnnotationTransfer annotationTransfer, Matrix<A> matrix) throws MatrixException {
		return Convert.calcNew(annotationTransfer, matrix);
	}

	public static final <A>Matrix<A> copyOf(Matrix<A> matrix) throws MatrixException {
		return Convert.calcNew(AnnotationTransfer.LINK, matrix);
	}

	public static final <A>Matrix<A> randn(long... size) throws MatrixException {
		return Randn.calc(size);
	}

	public static final <A>Matrix<A> randn(EntryType entryType, long... size) throws MatrixException {
		return Randn.calc(entryType, size);
	}

	public static final <A>Matrix<A> rand(long... size) throws MatrixException {
		return Rand.calc(size);
	}

	public static final <A>Matrix<A> rand(EntryType entryType, long... size) throws MatrixException {
		return Rand.calc(entryType, size);
	}

	public static final <A>Matrix<A> copyOf(EntryType newEntryType, AnnotationTransfer annotationTransfer, Matrix<?> matrix)
			throws MatrixException {
		return Convert.calcNew(newEntryType, annotationTransfer, matrix);
	}

	public static final <A>Matrix<A> correlatedColumns(int rows, int columns, double correlationFactor)
			throws MatrixException {
		Matrix ret = MatrixFactory.zeros(rows, columns);

		Matrix orig = MatrixFactory.randn(rows, 1);

		for (int c = 0; c < columns; c++) {
			Matrix rand = MatrixFactory.randn(rows, 1);

			for (int r = 0; r < rows; r++) {
				ret.setDouble((orig.getDouble(r, 0) * correlationFactor)
						+ ((1.0 - correlationFactor) * rand.getDouble(r, 0)), r, c);
			}
		}

		return ret;
	}

	public static final <A>Matrix<A> sharedInstance(Matrix<A> m) throws MatrixException {
		try {
			Class<?> c = Class.forName("org.jdmp.jgroups.ReplicatedSparseMatrix");
			Constructor<?> constr = c.getConstructor(new Class[] { Matrix.class });
			Matrix matrix = (Matrix) constr.newInstance(new Object[] { m });
			return matrix;
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	public static final DefaultDenseDoubleMatrix2D fromValue(double value) {
		return new DefaultDenseDoubleMatrix2D(new double[][] { { value } });
	}

	public static Matrix zeros(EntryType entryType, long... size) throws MatrixException {
		try {
			switch (entryType) {
			case DOUBLE:
				return zeros(size);
			case OBJECT:
				return new DefaultDenseObjectMatrix2D(size);
			case STRING:
				return new DefaultDenseStringMatrix2D(size);
			default:
				throw new MatrixException("entry type not yet supported: " + entryType);
			}
		} catch (Exception e) {
			throw new MatrixException("could not create Matrix", e);
		}
	}

	public static <A>Matrix<A> ones(long... size) throws MatrixException {
		return Ones.calc(size);
	}

	public static <A>Matrix<A> fill(Object value, long... size) throws MatrixException {
		return Fill.calc(value, size);
	}

	public static <A>Matrix<A> ones(EntryType entryType, long... size) throws MatrixException {
		return Ones.calc(entryType, size);
	}

	public static <A>Matrix<A> eye(long... size) throws MatrixException {
		return Eye.calc(size);
	}

	public static <A>Matrix<A> eye(EntryType entryType, long... size) throws MatrixException {
		return Eye.calc(entryType, size);
	}

	public static final <A>Matrix<A> createVectorForClass(int classID, int classCount) throws MatrixException {
		Matrix matrix = MatrixFactory.zeros(classCount, 1);
		matrix.setDouble(1.0, classID, 0);
		return matrix;
	}

	public static final DefaultDenseObjectMatrix2D fromArray(Object[]... valueList) {
		return new DefaultDenseObjectMatrix2D(valueList);
	}

	public static final FileListMatrix linkToDir(String dir) {
		return new FileListMatrix(dir);
	}

	@SuppressWarnings("unchecked")
	public static final DefaultMapMatrix fromMap(Map map) {
		return new DefaultMapMatrix(map);
	}

	public static final DefaultListMatrix fromArray(Object... objects) {
		return new DefaultListMatrix<Object>(objects);
	}

	@SuppressWarnings("unchecked")
	public static final DefaultListMatrix fromCollection(Collection list) {
		return new DefaultListMatrix<Object>(list);
	}

	public static <A>Matrix<A> fromString(String string, Object... parameters) {
		return Import.fromString(string, parameters);
	}

	public static <A>Matrix<A> fromString(Format format, String string, Object... parameters) throws MatrixException {
		return Import.fromString(format, string, parameters);
	}

	/**
	 * Wraps another Matrix so that all methods are executed synchronized.
	 * 
	 * @param matrix
	 *            the source Matrix
	 * @return a synchronized Matrix
	 */
	public static final <A>SynchronizedMatrix<A> synchronizedMatrix(Matrix<A> matrix) {
		return new SynchronizedMatrix<A>(matrix);
	}

	public static final DoubleMatrix linkToBinaryFile(String filename, int rowCount, int columnCount) {
		return new DenseFileMatrix2D(new File(filename), rowCount, columnCount);
	}

	public static final Matrix linkToJDBC(String url, String username, String password, String tablename) {
		try {
			Class<?> c = Class.forName("org.jdmp.odbc.FullODBCMatrix2D");
			Constructor<?> constr = c.getConstructor(new Class[] { String.class, String.class, String.class,
					String.class });
			Matrix odbcMatrix = (Matrix) constr.newInstance(new Object[] { url, username, password, tablename });
			return new BufferedObjectMatrix(odbcMatrix);
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	public static final Matrix linkToJDBC(String url, String username, String password, String tablename,
			String idColumn) {
		try {
			Class<?> c = Class.forName("org.jdmp.odbc.FullODBCMatrix2D");
			Constructor<?> constr = c.getConstructor(new Class[] { String.class, String.class, String.class,
					String.class, String.class });
			Matrix odbcMatrix = (Matrix) constr
					.newInstance(new Object[] { url, username, password, tablename, idColumn });
			return new BufferedObjectMatrix(odbcMatrix);
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	public final static Matrix sparse(long... size) throws MatrixException {
		try {
			return AbstractGenericMatrix.getSparseDoubleMatrix2DConstructor().newInstance(size);
		} catch (Exception e) {
			throw new MatrixException("could not create Matrix", e);
		}
	}

	public final static Matrix sparse(EntryType entryType, long... size) throws MatrixException {
		switch (entryType) {
		case DOUBLE:
			return sparse(size);
		default:
			throw new MatrixException("entry type not yet supported: " + entryType);
		}
	}

	public static Matrix zeros(long... size) throws MatrixException {
		try {
			return AbstractGenericMatrix.getFullDoubleMatrix2DConstructor().newInstance(size);
		} catch (Exception e) {
			if (e.getCause() instanceof OutOfMemoryError) {
				logger.log(Level.WARNING, "matrix does not fit into memory, creating sparse Matrix instead");
				return sparse(size);
			}
			throw new MatrixException("could not create Matrix", e);
		}
	}

	public static final Matrix linkToFile(Format format, File file, Object... parameters) throws MatrixException,
			IOException {
		return Import.linkToFile(format, file, parameters);
	}

	public static final Matrix importFromFile(String filename, Object... parameters) throws MatrixException {
		return Import.importFromFile(new File(filename), parameters);
	}

	public static final Matrix importFromFile(File file, Object... parameters) throws MatrixException {
		return Import.importFromFile(file, parameters);
	}

	public static final Matrix importFromFile(Format format, String file, Object... parameters) throws MatrixException {
		return Import.importFromFile(format, new File(file), parameters);
	}

	public static final Matrix importFromFile(Format format, File file, Object... parameters) throws MatrixException {
		return Import.importFromFile(format, file, parameters);
	}

}
