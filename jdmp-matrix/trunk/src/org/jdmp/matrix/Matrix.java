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

import org.jdmp.matrix.calculation.Calculation.Ret;
import org.jdmp.matrix.calculation.basic.Convert;
import org.jdmp.matrix.calculation.basic.DiscretizeToColumns;
import org.jdmp.matrix.calculation.entrywise.creators.Eye;
import org.jdmp.matrix.calculation.entrywise.creators.Fill;
import org.jdmp.matrix.calculation.entrywise.creators.Ones;
import org.jdmp.matrix.calculation.entrywise.creators.Rand;
import org.jdmp.matrix.calculation.entrywise.creators.Randn;
import org.jdmp.matrix.implementations.basic.DefaultDenseDoubleMatrix2D;
import org.jdmp.matrix.implementations.basic.DefaultDenseObjectMatrix2D;
import org.jdmp.matrix.implementations.basic.DefaultDenseStringMatrix2D;
import org.jdmp.matrix.implementations.basic.DefaultSparseObjectMatrix;
import org.jdmp.matrix.implementations.basic.SynchronizedMatrix;
import org.jdmp.matrix.implementations.collections.DefaultListMatrix;
import org.jdmp.matrix.implementations.collections.DefaultMapMatrix;
import org.jdmp.matrix.implementations.io.FileListMatrix;
import org.jdmp.matrix.implementations.io.DenseFileMatrix2D;
import org.jdmp.matrix.implementations.misc.BufferedObjectMatrix;
import org.jdmp.matrix.implementations.misc.ReshapedMatrix;
import org.jdmp.matrix.implementations.misc.SubMatrix;
import org.jdmp.matrix.interfaces.MatrixInterfaces;
import org.jdmp.matrix.io.Export;
import org.jdmp.matrix.io.Import;
import org.jdmp.matrix.util.JDMPSettings;
import org.jdmp.matrix.util.MathUtil;
import org.jdmp.matrix.util.StringUtil;

/**
 * <code>Matrix</code> is the main class for storing any type of data. You
 * have to choose the suitable implementation for your needs, e.g.
 * <code>DefaultFullDoubleMatrix2D</code> to store double values or
 * DefaultGenericMatrix if you want to specify the object type.
 * 
 * 
 * @author Holger Arndt
 * @version $Revision$
 * @date $Date$
 * 
 * @log $Log$
 * 
 */
public abstract class Matrix implements MatrixInterfaces {

	/**
	 * A logger used for <code>Matrix</code> and all subclasses
	 */
	protected transient static final Logger logger = Logger.getLogger(Matrix.class.getName());

	/**
	 * Defines the object types that can be stored in a Matrix. Different matrix
	 * implementation might exist for each type. Use EntryType.OBJECT for other
	 * object types.
	 */
	public enum EntryType {
		BOOLEAN, BYTE, CHAR, SHORT, INT, LONG, FLOAT, DOUBLE, STRING, DATE, OBJECT
	};

	public enum AnnotationTransfer {
		NONE, LINK, COPY
	};

	private static String fullDoubleMatrix2DClassName = "org.jdmp.mtj.MTJFullDoubleMatrix2D";

	private static String fullObjectMatrix2DClassName = DefaultDenseObjectMatrix2D.class.getName();

	private static String fullStringMatrix2DClassName = DefaultDenseStringMatrix2D.class.getName();

	private static String sparseDoubleMatrix2DClassName = DefaultSparseObjectMatrix.class.getName();

	private static String sparseObjectMatrix2DClassName = DefaultSparseObjectMatrix.class.getName();

	private static Constructor<? extends Matrix> fullDoubleMatrix2DConstructor = null;

	private static Constructor<? extends Matrix> fullObjectMatrix2DConstructor = null;

	private static Constructor<? extends Matrix> sparseDoubleMatrix2DConstructor = null;

	private static Constructor<? extends Matrix> sparseObjectMatrix2DConstructor = null;

	public static final int Y = 0;

	public static final int X = 1;

	public static final int Z = 2;

	public static final int ROW = 0;

	public static final int COLUMN = 1;

	public static final int ALL = 0x7fffffff;

	public static final int NONE = -1;

	/**
	 * Import and export formats that are supported.
	 */
	public enum Format {
		CSV, TXT, M, MAT, HTML, MTX, XLS, OBJ, SER, GraphML, TEX, WAV, BMP, TIFF, PLT, JPEG, PDF, PNG, XML, AML, ARFF, ATT, LOG, NET, XRFF
	};

	static {
		JDMPSettings.initialize();
		long mem = Runtime.getRuntime().maxMemory();
		if (mem < 133234688) {
			logger.log(Level.WARNING, "Available memory is very low: " + (mem / 1024 / 1024) + "M");
			logger.log(Level.FINE, "Use the parameter -Xmx512M to increase the available memory");
		}
	}

	/**
	 * Wraps another Matrix so that all methods are executed synchronized.
	 * 
	 * @param matrix
	 *            the source Matrix
	 * @return a synchronized Matrix
	 */
	public static final SynchronizedMatrix synchronizedMatrix(Matrix matrix) {
		return new SynchronizedMatrix(matrix);
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

	public abstract void exportToFile(Format format, File file, Object... parameters) throws MatrixException;

	public abstract void exportToFile(Format format, String file, Object... parameters) throws MatrixException;

	public abstract void exportToFile(File file, Object... parameters) throws MatrixException;

	public abstract void exportToFile(String file, Object... parameters) throws MatrixException;

	public static final DefaultDenseObjectMatrix2D fromArray(Object[]... valueList) {
		return new DefaultDenseObjectMatrix2D(valueList);
	}

	public static final FileListMatrix linkToDir(String dir) {
		return new FileListMatrix(dir);
	}

	@SuppressWarnings("unchecked")
	public static final DefaultMapMatrix fromMap(Map map) {
		return new DefaultMapMatrix<Object, Object>(map);
	}

	public static final DefaultListMatrix fromArray(Object... objects) {
		return new DefaultListMatrix<Object>(objects);
	}

	@SuppressWarnings("unchecked")
	public static final DefaultListMatrix fromCollection(Collection list) {
		return new DefaultListMatrix<Object>(list);
	}

	public static final Matrix correlatedColumns(int rows, int columns, double correlationFactor)
			throws MatrixException {
		Matrix ret = Matrix.zeros(rows, columns);

		Matrix orig = Matrix.randn(rows, 1);

		for (int c = 0; c < columns; c++) {
			Matrix rand = Matrix.randn(rows, 1);

			for (int r = 0; r < rows; r++) {
				ret.setDouble((orig.getDouble(r, 0) * correlationFactor)
						+ ((1.0 - correlationFactor) * rand.getDouble(r, 0)), r, c);
			}
		}

		return ret;
	}

	public static final Matrix sharedInstance(Matrix m) {
		try {
			Class<?> c = Class.forName("org.jdmp.jgroups.ReplicatedSparseMatrix");
			Constructor<?> constr = c.getConstructor(new Class[] { Matrix.class });
			Matrix matrix = (Matrix) constr.newInstance(new Object[] { m });
			return matrix;
		} catch (Exception e) {
			logger.log(Level.WARNING, "cannot connect", e);
			return null;
		}
	}

	public static final DefaultDenseDoubleMatrix2D fromValue(double value) {
		return new DefaultDenseDoubleMatrix2D(new double[][] { { value } });
	}

	public final boolean containsMissingValues() throws MatrixException {
		for (long[] c : allCoordinates()) {
			double v = getDouble(c);
			if (v != v || v == Double.NEGATIVE_INFINITY || v == Double.POSITIVE_INFINITY) {
				return true;
			}
		}
		return false;
	}

	public static final Matrix copyFromArray(double[]... values) {
		int rows = values.length;
		int columns = 0;
		for (int i = values.length - 1; i >= 0; i--) {
			columns = Math.max(columns, values[i].length);
		}
		Matrix m = Matrix.zeros(rows, columns);
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

	public static final Matrix copyOf(AnnotationTransfer annotationTransfer, Matrix matrix) throws MatrixException {
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

	public static final Matrix copyOf(EntryType newEntryType, AnnotationTransfer annotationTransfer, Matrix matrix)
			throws MatrixException {
		return Convert.calcNew(newEntryType, annotationTransfer, matrix);
	}

	public static void setFullDoubleMatrix2DClassName(String fullDoubleMatrix2DClassName) {
		Matrix.fullDoubleMatrix2DClassName = fullDoubleMatrix2DClassName;
		Matrix.fullDoubleMatrix2DConstructor = null;
	}

	public static void setSparseDoubleMatrix2DClassName(String sparseDoubleMatrix2DClassName) {
		Matrix.sparseDoubleMatrix2DClassName = sparseDoubleMatrix2DClassName;
		Matrix.sparseDoubleMatrix2DConstructor = null;
	}

	public static Matrix zeros(long... size) throws MatrixException {
		try {
			return getFullDoubleMatrix2DConstructor().newInstance(size);
		} catch (Exception e) {
			if (e.getCause() instanceof OutOfMemoryError) {
				logger.log(Level.WARNING, "matrix does not fit into memory, creating sparse Matrix instead");
				return sparse(size);
			}
			throw new MatrixException("could not create Matrix", e);
		}
	}

	private static Constructor<? extends Matrix> getFullDoubleMatrix2DConstructor() throws Exception {
		if (fullDoubleMatrix2DConstructor == null) {
			Class<?> fullDoubleMatrix2DClass = null;
			try {
				fullDoubleMatrix2DClass = Class.forName(fullDoubleMatrix2DClassName);
			} catch (ClassNotFoundException e) {
				logger.log(Level.WARNING, "Could not find desired Matrix implementation: "
						+ fullDoubleMatrix2DClassName);
				logger.log(Level.INFO, "Falling back to DefaultFullDoubleMatrix2D.");
				logger.log(Level.INFO, "To speed up Matrix calculations, you should add jdmp-mtj to the classpath.");
				fullDoubleMatrix2DClass = DefaultDenseDoubleMatrix2D.class;
			}
			Class<?> p = null;
			// TODO: this should be solved in a more efficient way
			for (Constructor<?> co : fullDoubleMatrix2DClass.getConstructors()) {
				if ("long[]".equals(co.getParameterTypes()[0].getCanonicalName())) {
					p = co.getParameterTypes()[0];
				}
			}
			fullDoubleMatrix2DConstructor = (Constructor<Matrix>) fullDoubleMatrix2DClass.getConstructor(p);
		}
		return fullDoubleMatrix2DConstructor;
	}

	private static Constructor<? extends Matrix> getSparseDoubleMatrix2DConstructor() throws Exception {
		if (sparseDoubleMatrix2DConstructor == null) {
			Class<? extends Matrix> sparseDoubleMatrix2DClass = null;
			try {
				sparseDoubleMatrix2DClass = (Class<? extends Matrix>) Class.forName(sparseDoubleMatrix2DClassName);
			} catch (ClassNotFoundException e) {
				logger.log(Level.WARNING, "Could not find desired Matrix implementation: "
						+ sparseDoubleMatrix2DClassName);
				logger.log(Level.INFO, "Falling back to DefaultSparseObjectMatrix.");
				logger.log(Level.INFO, "To speed up Matrix calculations, you should add jdmp-mtj to the classpath.");
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
				logger.log(Level.WARNING, "entry type not yet supported: " + entryType);
				return null;
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

	public static final Matrix createVectorForClass(int classID, int classCount) throws MatrixException {
		Matrix matrix = Matrix.zeros(classCount, 1);
		matrix.setDouble(1.0, classID, 0);
		return matrix;
	}

	public final Matrix convertIntToVector(int numberOfClasses) throws MatrixException {
		Matrix m = Matrix.zeros(numberOfClasses, 1);
		for (int i = numberOfClasses - 1; i != -1; i--) {
			m.setDouble(-1.0, i, 0);
		}
		m.setDouble(1.0, (int) getDouble(0, 0), 0);
		return m;
	}

	public static final Matrix linkToBinaryFile(String filename, int rowCount, int columnCount) {
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
			logger.log(Level.WARNING, "cannot connect", e);
			return null;
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
			logger.log(Level.WARNING, "cannot connect", e);
			return null;
		}
	}

	public final double getEuklideanValue() throws MatrixException {
		double sum = 0.0;
		for (long[] c : allCoordinates()) {
			sum += Math.pow(getDouble(c), 2.0);
		}
		return Math.sqrt(sum);
	}

	public Matrix deleteColumnsWithMissingValues(Ret returnType) throws MatrixException {
		Matrix mv = countMissing(Ret.NEW, Matrix.ROW);
		List<Long> sel = new ArrayList<Long>();
		for (long c = 0; c < mv.getColumnCount(); c++) {
			if (mv.getDouble(0, c) == 0.0)
				sel.add(c);
		}
		long[] longsel = new long[sel.size()];
		for (int i = sel.size(); --i >= 0;) {
			longsel[i] = sel.get(i);
		}
		return selectColumns(returnType, longsel);
	}

	public Matrix replaceMissingBy(Matrix matrix) throws MatrixException {
		Matrix ret = Matrix.zeros(getSize());
		for (long[] c : allCoordinates()) {
			double v = getDouble(c);
			if (MathUtil.isNaNOrInfinite(v)) {
				ret.setDouble(matrix.getDouble(c), c);
			} else {
				ret.setDouble(getDouble(c), c);
			}
		}
		return ret;
	}

	public Matrix deleteRowsWithMissingValues(Ret returnType) throws MatrixException {
		Matrix mv = countMissing(Ret.NEW, Matrix.COLUMN);
		List<Long> sel = new ArrayList<Long>();
		for (long r = 0; r < mv.getRowCount(); r++) {
			if (mv.getDouble(r, 0) == 0.0)
				sel.add(r);
		}
		long[] longsel = new long[sel.size()];
		for (int i = sel.size(); --i >= 0;) {
			longsel[i] = sel.get(i);
		}
		return selectRows(returnType, longsel);
	}

	public final double getRMS() throws MatrixException {
		double sum = 0.0;
		long count = 0;
		for (long[] c : allCoordinates()) {
			sum += Math.pow(getDouble(c), 2.0);
			count++;
		}
		sum /= count;
		return Math.sqrt(sum);
	}

	public final double getValueSum() throws MatrixException {
		double sum = 0.0;
		for (long[] c : allCoordinates()) {
			sum += getDouble(c);
		}
		return sum;
	}

	public final double getValueMean() throws MatrixException {
		return getValueSum() / getValueCount();
	}

	public final double getAbsoluteValueSum() throws MatrixException {
		double sum = 0.0;
		for (long[] c : allCoordinates()) {
			sum += Math.abs(getDouble(c));
		}
		return sum;
	}

	public final double getAbsoluteValueMean() throws MatrixException {
		return getAbsoluteValueSum() / getValueCount();
	}

	public final String getColumnLabel(int col) {
		return StringUtil.format(getAxisAnnotation(COLUMN, col));
	}

	public final String getRowLabel(int row) {
		return StringUtil.format(getAxisAnnotation(ROW, row));
	}

	public final Object getRowObject(int row) {
		return getAxisAnnotation(ROW, row);
	}

	public final Object getColumnObject(int col) {
		return getAxisAnnotation(COLUMN, col);
	}

	public final void setColumnLabel(int col, String label) {
		setAxisAnnotation(COLUMN, col, label);
	}

	public final void setRowLabel(int row, String label) {
		setAxisAnnotation(ROW, row, label);
	}

	public final void setRowObject(int row, Object o) {
		setAxisAnnotation(ROW, row, o);
	}

	public final void setColumnObject(int col, Object o) {
		setAxisAnnotation(COLUMN, col, o);
	}

	public final void addNoise_(double noiselevel) throws MatrixException {
		for (long[] c : allCoordinates()) {
			setDouble(getDouble(c) + MathUtil.nextGaussian(0.0, noiselevel), c);
		}
	}

	public final void greaterOrZero_() throws MatrixException {
		for (long[] c : allCoordinates()) {
			double v = getDouble(c);
			setDouble(v < 0.0 ? 0.0 : v, c);
		}
	}

	public final Matrix subMatrixCopy(long[] start, long[] end) throws MatrixException {
		return subMatrixCopy(start[ROW], start[COLUMN], end[ROW], end[COLUMN]);
	}

	public final Matrix subMatrixCopy(long startRow, long startColumn, long endRow, long endColumn)
			throws MatrixException {
		long[] newSize = new long[] { endRow - startRow + 1, endColumn - startColumn + 1 };
		Matrix result = Matrix.zeros(newSize);
		for (long[] c : allCoordinates()) {
			if (c[ROW] >= startRow && c[ROW] < endRow + 1 && c[COLUMN] >= startColumn && c[COLUMN] < endColumn + 1) {
				long[] target = Coordinates.copyOf(c);
				target[ROW] -= startRow;
				target[COLUMN] -= startColumn;
				result.setDouble(getDouble(c), target);
			}
		}
		return result;
	}

	public final void scaleRowsToOne_() throws MatrixException {
		for (long r = getRowCount() - 1; r != -1; r--) {
			double sum = 0.0;
			for (long c = getColumnCount() - 1; c != -1; c--) {
				sum += Math.abs(getDouble(r, c));
			}
			sum = sum / getRowCount();
			for (long c = getColumnCount() - 1; c != -1; c--) {
				setDouble(getDouble(r, c) / sum, r, c);
			}
		}
	}

	public final double getDoubleValue() throws MatrixException {
		return getEuklideanValue();
	}

	public static final Matrix horCat(Matrix... matrices) throws MatrixException {
		return concat(COLUMN, matrices);
	}

	public static final Matrix vertCat(Matrix... matrices) throws MatrixException {
		return concat(ROW, matrices);
	}

	public static final Matrix vertCat(Collection<Matrix> matrices) throws MatrixException {
		return concat(ROW, matrices);
	}

	public static final Matrix horCat(Collection<Matrix> matrices) throws MatrixException {
		return concat(COLUMN, matrices);
	}

	public static final Matrix concat(int dimension, Matrix... matrices) throws MatrixException {
		Matrix result = Matrix.copyOf(AnnotationTransfer.COPY, matrices[0]);
		for (int i = 1; i < matrices.length; i++) {
			result = result.append(dimension, matrices[i]);
		}
		return result;
	}

	public static final Matrix concat(int dimension, Collection<Matrix> matrices) throws MatrixException {
		List<Matrix> list = new ArrayList<Matrix>(matrices);
		Matrix result = Matrix.copyOf(AnnotationTransfer.COPY, list.get(0));
		for (int i = 1; i < matrices.size(); i++) {
			result = result.append(dimension, list.get(i));
		}
		return result;
	}

	public final Matrix appendHorizontally(Matrix m) throws MatrixException {
		return append(COLUMN, m);
	}

	public final Matrix appendVertically(Matrix m) throws MatrixException {
		return append(ROW, m);
	}

	public final Matrix append(int dimension, Matrix m) throws MatrixException {
		long[] newSize = Coordinates.copyOf(getSize());
		newSize[dimension] += m.getSize()[dimension];
		Matrix result = Matrix.zeros(newSize);
		for (long[] c : allCoordinates()) {
			result.setDouble(getDouble(c), c);
		}
		for (long[] c : m.allCoordinates()) {
			long[] newC = Coordinates.copyOf(c);
			newC[dimension] += getSize()[dimension];
			result.setDouble(m.getDouble(c), newC);
		}
		return result;
	}

	public final Matrix subMatrixLink(long[] start, long[] size) {
		return new SubMatrix(this, start, size);
	}

	public final Matrix subMatrixLink(long startRow, long startColumn, long rowCount, long columnCount) {
		return new SubMatrix(this, new long[] { startRow, startColumn }, new long[] { rowCount, columnCount });
	}

	public void setSize(long... size) {
		throw new MatrixException("operation not possible: cannot change size of matrix");
	}

	@SuppressWarnings("unchecked")
	public Matrix[] svd() throws MatrixException {
		try {
			Class<? extends Matrix> mtjc = (Class<? extends Matrix>) Class
					.forName("org.jdmp.mtj.MTJFullDoubleMatrix2D");
			Constructor<? extends Matrix> con = mtjc.getConstructor(Matrix.class);
			Matrix mtjm = con.newInstance(this);

			return mtjm.svd();
		} catch (ClassNotFoundException e) {
			throw new MatrixException("cannot calculate SVD: add jdmp-mtj to classpath");
		} catch (Exception e) {
			throw new MatrixException(e);
		}
	}

	public final void rescaleEntries_(int axis, double targetMin, double targetMax) throws MatrixException {
		if (axis == ALL) {
			this.toRowVector().rescaleEntries(0, targetMin, targetMax);
		} else {

		}
		double minValue = getMinValue();
		double maxValue = getMaxValue();
		double targetDiff = targetMax - targetMin;
		double diffBefore = maxValue - minValue;
		if (diffBefore != 0) {
			// TODO: this calculation is not correct
			double scale = targetDiff / diffBefore;
			double offet = targetMin - minValue;
			for (long[] c : allCoordinates()) {
				setDouble(getDouble(c) * scale + offet, c);
			}
		}
		notifyGUIObject();
	}

	public final Matrix toRowVector() throws MatrixException {
		if (isRowVector()) {
			return this;
		} else if (isColumnVector()) {
			return transpose();
		} else {
			return reshape(Coordinates.product(getSize()), 1);
		}
	}

	public final Matrix toColumnVector() throws MatrixException {
		if (isColumnVector()) {
			return this;
		} else if (isRowVector()) {
			return transpose();
		} else {
			return reshape(1, (int) Coordinates.product(getSize()));
		}
	}

	public String toString(Format format) throws MatrixException {
		return Export.toString(format, this);
	}

	public final Matrix reshape(long... newSize) {
		return new ReshapedMatrix(this, newSize);
	}

	public final Matrix rescaleEntries() throws MatrixException {
		return rescaleEntries(ALL, -1.0, 1.0);
	}

	public final void rescaleEntries_() throws MatrixException {
		rescaleEntries_(ALL, -1.0, 1.0);
	}

	public final Matrix rescaleEntries(int axis, double targetMin, double targetMax) throws MatrixException {
		Matrix ret = Matrix.zeros(getSize());
		ret.rescaleEntries_(axis, targetMin, targetMax);
		return ret;
	}

	public final Matrix discretizeToColumns(long column) throws MatrixException {
		return new DiscretizeToColumns(this, false, column).calc(Ret.NEW);
	}

	public final void fadeIn_(int axis, long start, long end) throws MatrixException {
		if (axis == ALL) {
			this.toRowVector().fadeIn_(0, start, end);
		} else if (axis == ROW) {
			double stepsize = 1.0 / (end - start);
			for (long r = start, i = 0; r < end; r++) {
				double factor = (++i * stepsize);
				for (int c = 0; c < getSize()[COLUMN]; c++) {
					setDouble(getDouble(r, c) * factor, r, c);
				}
			}
		} else if (axis == COLUMN) {
			double stepsize = 1.0 / (end - start);
			for (long c = start, i = 0; c < end; c++) {
				double factor = (++i * stepsize);
				for (int r = 0; r < getSize()[ROW]; r++) {
					setDouble(getDouble(r, c) * factor, r, c);
				}
			}
		}
		notifyGUIObject();
	}

	public final void fadeOut_(int axis, long start, long end) throws MatrixException {
		if (axis == ALL) {
			this.toRowVector().fadeOut_(0, start, end);
		} else if (axis == ROW) {
			double stepsize = 1.0 / (end - start);
			for (long r = start, i = 0; r < end; r++) {
				double factor = 1.0 - (++i * stepsize);
				for (int c = 0; c < getSize()[COLUMN]; c++) {
					setDouble(getDouble(r, c) * factor, r, c);
				}
			}
		} else if (axis == COLUMN) {
			double stepsize = 1.0 / (end - start);
			for (long c = start, i = 0; c < end; c++) {
				double factor = 1.0 - (++i * stepsize);
				for (int r = 0; r < getSize()[ROW]; r++) {
					setDouble(getDouble(r, c) * factor, r, c);
				}
			}
		}
		notifyGUIObject();
	}

	public final void fadeIn_() throws MatrixException {
		fadeIn_(ROW, 0, getRowCount());
	}

	public final void fadeOut_() throws MatrixException {
		fadeOut_(ROW, 0, getRowCount());
	}

	public final static Matrix sparse(long... size) throws MatrixException {
		try {
			return getSparseDoubleMatrix2DConstructor().newInstance(size);
		} catch (Exception e) {
			throw new MatrixException("could not create Matrix", e);
		}
	}

	public final static Matrix sparse(EntryType entryType, long... size) throws MatrixException {
		switch (entryType) {
		case DOUBLE:
			return sparse(size);
		default:
			logger.log(Level.WARNING, "entry type not yet supported: " + entryType);
			return null;
		}
	}

	public Matrix addColumnWithOnes() throws MatrixException {
		Matrix ret = Matrix.zeros(getRowCount(), getColumnCount() + 1);
		for (long[] c : allCoordinates()) {
			ret.setDouble(getDouble(c), c);
		}
		for (long r = getRowCount() - 1; r != -1; r--) {
			ret.setDouble(1.0, r, getColumnCount());
		}
		return ret;
	}

	public Matrix addRowWithOnes() throws MatrixException {
		Matrix ret = Matrix.zeros(getRowCount() + 1, getColumnCount());
		for (long[] c : allCoordinates()) {
			ret.setDouble(getDouble(c), c);
		}
		for (long c = getColumnCount() - 1; c != -1; c--) {
			ret.setDouble(1.0, getRowCount(), c);
		}
		return ret;
	}

	public static Matrix fromString(String string, Object... parameters) {
		return Import.fromString(string, parameters);
	}

	public static Matrix fromString(Format format, String string, Object... parameters) throws MatrixException {
		return Import.fromString(format, string, parameters);
	}

	public abstract Matrix clone();

}
