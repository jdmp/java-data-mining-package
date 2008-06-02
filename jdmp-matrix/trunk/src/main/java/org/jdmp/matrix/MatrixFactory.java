/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
 *
 * This file is part of the Java Data Mining Package (JDMP).
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership and licensing.
 *
 * JDMP is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * JDMP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with JDMP; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.jdmp.matrix;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
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
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.implementations.basic.DefaultDenseDoubleMatrix2D;
import org.jdmp.matrix.implementations.basic.DefaultDenseObjectMatrix2D;
import org.jdmp.matrix.implementations.basic.DefaultDenseStringMatrix2D;
import org.jdmp.matrix.implementations.basic.DefaultSparseObjectMatrix;
import org.jdmp.matrix.implementations.basic.SynchronizedGenericMatrix;
import org.jdmp.matrix.implementations.collections.DefaultListMatrix;
import org.jdmp.matrix.implementations.collections.DefaultMapMatrix;
import org.jdmp.matrix.implementations.io.DenseFileMatrix2D;
import org.jdmp.matrix.implementations.io.FileListMatrix;
import org.jdmp.matrix.implementations.misc.BufferedObjectMatrix;
import org.jdmp.matrix.io.ImportMatrix;
import org.jdmp.matrix.io.LinkMatrix;
import org.jdmp.matrix.util.MathUtil;

/**
 * This class provides a factory for matrix generation.
 * 
 * 
 * 
 * @author Andreas Naegele
 */
public abstract class MatrixFactory {

	protected transient static final Logger logger = Logger
			.getLogger(MatrixFactory.class.getName());

	public static final int ROW = Matrix.ROW;

	public static final int COLUMN = Matrix.COLUMN;

	public static final int Y = Matrix.Y;

	public static final int X = Matrix.X;

	public static final int Z = Matrix.Z;

	public static final int ALL = Matrix.ALL;

	public static final int NONE = Matrix.NONE;

	private static String denseDoubleMatrix2DClassName = "org.jdmp.mtj.MTJDenseDoubleMatrix2D";

	private static String denseObjectMatrix2DClassName = DefaultDenseObjectMatrix2D.class.getName();

	private static String denseStringMatrix2DClassName = DefaultDenseStringMatrix2D.class.getName();

	private static String sparseDoubleMatrix2DClassName = DefaultSparseObjectMatrix.class.getName();

	private static String sparseObjectMatrix2DClassName = DefaultSparseObjectMatrix.class.getName();

	private static Constructor<? extends Matrix> denseDoubleMatrix2DConstructor = null;

	private static Constructor<? extends Matrix> denseObjectMatrix2DConstructor = null;

	private static Constructor<? extends Matrix> sparseDoubleMatrix2DConstructor = null;

	private static Constructor<? extends Matrix> sparseObjectMatrix2DConstructor = null;

	public static void setDenseDoubleMatrix2DClassName(String denseDoubleMatrix2DClassName) {
		MatrixFactory.denseDoubleMatrix2DClassName = denseDoubleMatrix2DClassName;
		MatrixFactory.denseDoubleMatrix2DConstructor = null;
	}

	public static void setSparseDoubleMatrix2DClassName(String sparseDoubleMatrix2DClassName) {
		MatrixFactory.sparseDoubleMatrix2DClassName = sparseDoubleMatrix2DClassName;
		MatrixFactory.sparseDoubleMatrix2DConstructor = null;
	}

	public static Constructor<? extends Matrix> getDenseDoubleMatrix2DConstructor()
			throws Exception {
		if (denseDoubleMatrix2DConstructor == null) {
			Class<?> denseDoubleMatrix2DClass = null;
			try {
				denseDoubleMatrix2DClass = Class.forName(denseDoubleMatrix2DClassName);
			} catch (ClassNotFoundException e) {
				logger.log(Level.WARNING, "Could not find desired Matrix implementation: "
						+ denseDoubleMatrix2DClassName);
				logger.log(Level.INFO, "Falling back to DefaultDenseDoubleMatrix2D.");
				logger
						.log(Level.INFO,
								"To speed up Matrix calculations, you should add jdmp-mtj to the classpath.");
				denseDoubleMatrix2DClass = DefaultDenseDoubleMatrix2D.class;
			}
			Class<?> p = null;
			// TODO: this should be solved in a more efficient way
			for (Constructor<?> co : denseDoubleMatrix2DClass.getConstructors()) {
				if ("long[]".equals(co.getParameterTypes()[0].getCanonicalName())) {
					p = co.getParameterTypes()[0];
				}
			}
			denseDoubleMatrix2DConstructor = (Constructor<Matrix>) denseDoubleMatrix2DClass
					.getConstructor(p);
		}
		return denseDoubleMatrix2DConstructor;
	}

	@SuppressWarnings("unchecked")
	public static Constructor<? extends Matrix> getSparseDoubleMatrix2DConstructor()
			throws Exception {
		if (sparseDoubleMatrix2DConstructor == null) {
			Class<? extends Matrix> sparseDoubleMatrix2DClass = null;
			try {
				sparseDoubleMatrix2DClass = (Class<? extends Matrix>) Class
						.forName(sparseDoubleMatrix2DClassName);
			} catch (ClassNotFoundException e) {
				logger.log(Level.WARNING, "Could not find desired Matrix implementation: "
						+ sparseDoubleMatrix2DClassName);
				logger.log(Level.INFO, "Falling back to DefaultSparseObjectMatrix.");
				logger
						.log(Level.INFO,
								"To speed up Matrix calculations, you should add jdmp-mtj to the classpath.");
				sparseDoubleMatrix2DClass = DefaultSparseObjectMatrix.class;
			}
			Class<?> p = null;
			// TODO: this should be solved in a more efficient way
			for (Constructor<?> co : sparseDoubleMatrix2DClass.getConstructors()) {
				if ("long[]".equals(co.getParameterTypes()[0].getCanonicalName())) {
					p = co.getParameterTypes()[0];
				}
			}
			sparseDoubleMatrix2DConstructor = sparseDoubleMatrix2DClass.getConstructor(p);
		}
		return sparseDoubleMatrix2DConstructor;
	}

	public static final Matrix horCat(Matrix... matrices) throws MatrixException {
		return concat(COLUMN, matrices);
	}

	public static final <A> Matrix vertCat(Matrix... matrices) throws MatrixException {
		return concat(ROW, matrices);
	}

	public static final <A> Matrix vertCat(Collection<Matrix> matrices) throws MatrixException {
		return concat(ROW, matrices);
	}

	public static final Matrix horCat(Collection<Matrix> matrices) throws MatrixException {
		return concat(COLUMN, matrices);
	}

	public static final Matrix concat(int dimension, Matrix... matrices) throws MatrixException {
		Matrix result = MatrixFactory.copyOf(AnnotationTransfer.COPY, matrices[0]);
		for (int i = 1; i < matrices.length; i++) {
			result = result.append(dimension, matrices[i]);
		}
		return result;
	}

	public static final Matrix concat(int dimension, Collection<Matrix> matrices)
			throws MatrixException {
		List<Matrix> list = new ArrayList<Matrix>(matrices);
		Matrix result = MatrixFactory.copyOf(AnnotationTransfer.COPY, list.get(0));
		for (int i = 1; i < matrices.size(); i++) {
			result = result.append(dimension, list.get(i));
		}
		return result;
	}

	public static final Matrix copyFromArray(double[]... values) {
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

	public static final Matrix copyOf(AnnotationTransfer annotationTransfer, Matrix matrix)
			throws MatrixException {
		return Convert.calcNew(annotationTransfer, matrix);
	}

	public static final Matrix copyOf(Matrix matrix) throws MatrixException {
		return Convert.calcNew(AnnotationTransfer.LINK, matrix);
	}

	public static final Matrix randn(long... size) throws MatrixException {
		return Randn.calc(size);
	}

	public static final Matrix randn(EntryType entryType, long... size) throws MatrixException {
		return Randn.calc(entryType, size);
	}

	public static final Matrix rand(long... size) throws MatrixException {
		return Rand.calc(size);
	}

	public static final Matrix rand(EntryType entryType, long... size) throws MatrixException {
		return Rand.calc(entryType, size);
	}

	public static final Matrix copyOf(EntryType newEntryType,
			AnnotationTransfer annotationTransfer, Matrix matrix) throws MatrixException {
		return Convert.calcNew(newEntryType, annotationTransfer, matrix);
	}

	public static final Matrix correlatedColumns(int rows, int columns, double correlationFactor)
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

	public static final Matrix sharedInstance(Matrix m) throws MatrixException {
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
			case GENERIC:
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

	public static Matrix ones(long... size) throws MatrixException {
		return Ones.calc(size);
	}

	public static Matrix fill(Object value, long... size) throws MatrixException {
		return Fill.calc(value, size);
	}

	public static Matrix ones(EntryType entryType, long... size) throws MatrixException {
		return Ones.calc(entryType, size);
	}

	public static Matrix eye(long... size) throws MatrixException {
		return Eye.calc(size);
	}

	public static Matrix eye(EntryType entryType, long... size) throws MatrixException {
		return Eye.calc(entryType, size);
	}

	public static final Matrix createVectorForClass(int classID, int classCount)
			throws MatrixException {
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

	public static final DefaultListMatrix<Object> fromArray(Object... objects) {
		return new DefaultListMatrix<Object>(objects);
	}

	@SuppressWarnings("unchecked")
	public static final DefaultListMatrix fromCollection(Collection list) {
		return new DefaultListMatrix<Object>(list);
	}

	public static Matrix fromString(String string, Object... parameters) {
		return ImportMatrix.fromString(string, parameters);
	}

	public static Matrix fromString(Format format, String string, Object... parameters)
			throws MatrixException {
		return ImportMatrix.fromString(format, string, parameters);
	}

	/**
	 * Wraps another Matrix so that all methods are executed synchronized.
	 * 
	 * @param matrix
	 *            the source Matrix
	 * @return a synchronized Matrix
	 */
	public static final SynchronizedGenericMatrix<?> synchronizedMatrix(Matrix matrix) {
		return new SynchronizedGenericMatrix<Object>(matrix);
	}

	public static final DoubleMatrix linkToBinaryFile(String filename, int rowCount, int columnCount) {
		return new DenseFileMatrix2D(new File(filename), rowCount, columnCount);
	}

	public static final Matrix linkToJDBC(String url, String username, String password,
			String tablename) {
		try {
			Class<?> c = Class.forName("org.jdmp.odbc.DenseODBCMatrix2D");
			Constructor<?> constr = c.getConstructor(new Class[] { String.class, String.class,
					String.class, String.class });
			Matrix odbcMatrix = (Matrix) constr.newInstance(new Object[] { url, username, password,
					tablename });
			return new BufferedObjectMatrix(odbcMatrix);
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	public static final Matrix linkToJDBC(String url, String username, String password,
			String tablename, String idColumn) {
		try {
			Class<?> c = Class.forName("org.jdmp.odbc.DenseODBCMatrix2D");
			Constructor<?> constr = c.getConstructor(new Class[] { String.class, String.class,
					String.class, String.class, String.class });
			Matrix odbcMatrix = (Matrix) constr.newInstance(new Object[] { url, username, password,
					tablename, idColumn });
			return new BufferedObjectMatrix(odbcMatrix);
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	public final static Matrix sparse(long... size) throws MatrixException {
		try {
			return MatrixFactory.getSparseDoubleMatrix2DConstructor().newInstance(size);
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
			return MatrixFactory.getDenseDoubleMatrix2DConstructor().newInstance(size);
		} catch (Exception e) {
			if (e.getCause() instanceof OutOfMemoryError) {
				logger.log(Level.WARNING,
						"matrix does not fit into memory, creating sparse Matrix instead");
				return sparse(size);
			}
			throw new MatrixException("could not create Matrix", e);
		}
	}

	public static final Matrix linkToFile(Format format, File file, Object... parameters)
			throws MatrixException, IOException {
		return LinkMatrix.toFile(format, file, parameters);
	}

	public static final Matrix importFromFile(String filename, Object... parameters)
			throws MatrixException, IOException {
		return ImportMatrix.fromFile(new File(filename), parameters);
	}

	public static final Matrix importFromFile(File file, Object... parameters)
			throws MatrixException, IOException {
		return ImportMatrix.fromFile(file, parameters);
	}

	public static final Matrix importFromFile(Format format, String file, Object... parameters)
			throws MatrixException, IOException {
		return ImportMatrix.fromFile(format, new File(file), parameters);
	}

	public static final Matrix importFromFile(Format format, File file, Object... parameters)
			throws MatrixException, IOException {
		return ImportMatrix.fromFile(format, file, parameters);
	}

	public static Matrix importFromClipboard(Object... parameters) throws MatrixException {
		return importFromClipboard(Format.CSV, parameters);
	}

	public static Matrix importFromClipboard(Format format, Object... parameters)
			throws MatrixException {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable clipData = clipboard.getContents(null);
		String s;
		try {
			s = (String) (clipData.getTransferData(DataFlavor.stringFlavor));
		} catch (Exception ex) {
			s = ex.toString();
		}
		return fromString(format, s, parameters);
	}

}
