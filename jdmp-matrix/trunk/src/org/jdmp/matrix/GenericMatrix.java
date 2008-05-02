package org.jdmp.matrix;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Pattern;

import org.jdmp.matrix.calculation.AbstractCalculation;
import org.jdmp.matrix.calculation.Calculation;
import org.jdmp.matrix.calculation.Calculation.Calc;
import org.jdmp.matrix.calculation.Calculation.Ret;
import org.jdmp.matrix.calculation.basic.Convert;
import org.jdmp.matrix.calculation.basic.Deletion;
import org.jdmp.matrix.calculation.basic.Divide;
import org.jdmp.matrix.calculation.basic.Minus;
import org.jdmp.matrix.calculation.basic.Mtimes;
import org.jdmp.matrix.calculation.basic.Plus;
import org.jdmp.matrix.calculation.basic.Selection;
import org.jdmp.matrix.calculation.basic.Times;
import org.jdmp.matrix.calculation.basic.Transpose;
import org.jdmp.matrix.calculation.entrywise.basic.Abs;
import org.jdmp.matrix.calculation.entrywise.basic.Log;
import org.jdmp.matrix.calculation.entrywise.basic.Log10;
import org.jdmp.matrix.calculation.entrywise.basic.Log2;
import org.jdmp.matrix.calculation.entrywise.basic.Power;
import org.jdmp.matrix.calculation.entrywise.basic.Sign;
import org.jdmp.matrix.calculation.entrywise.basic.Sqrt;
import org.jdmp.matrix.calculation.entrywise.creators.Eye;
import org.jdmp.matrix.calculation.entrywise.creators.Fill;
import org.jdmp.matrix.calculation.entrywise.creators.Ones;
import org.jdmp.matrix.calculation.entrywise.creators.Rand;
import org.jdmp.matrix.calculation.entrywise.creators.Randn;
import org.jdmp.matrix.calculation.entrywise.creators.Zeros;
import org.jdmp.matrix.calculation.entrywise.hyperbolic.Cosh;
import org.jdmp.matrix.calculation.entrywise.hyperbolic.Sinh;
import org.jdmp.matrix.calculation.entrywise.hyperbolic.Tanh;
import org.jdmp.matrix.calculation.entrywise.replace.ReplaceRegex;
import org.jdmp.matrix.calculation.entrywise.rounding.Ceil;
import org.jdmp.matrix.calculation.entrywise.rounding.Floor;
import org.jdmp.matrix.calculation.entrywise.rounding.Round;
import org.jdmp.matrix.calculation.entrywise.trigonometric.Cos;
import org.jdmp.matrix.calculation.entrywise.trigonometric.Sin;
import org.jdmp.matrix.calculation.entrywise.trigonometric.Tan;
import org.jdmp.matrix.calculation.general.misc.Center;
import org.jdmp.matrix.calculation.general.misc.Standardize;
import org.jdmp.matrix.calculation.general.missingvalues.AddMissing;
import org.jdmp.matrix.calculation.general.missingvalues.CountMissing;
import org.jdmp.matrix.calculation.general.missingvalues.ImputeEM;
import org.jdmp.matrix.calculation.general.missingvalues.ImputeKNN;
import org.jdmp.matrix.calculation.general.missingvalues.ImputeMean;
import org.jdmp.matrix.calculation.general.missingvalues.ImputeRegression;
import org.jdmp.matrix.calculation.general.missingvalues.ImputeZero;
import org.jdmp.matrix.calculation.general.solving.Inv;
import org.jdmp.matrix.calculation.general.solving.Pinv;
import org.jdmp.matrix.calculation.general.solving.Princomp;
import org.jdmp.matrix.calculation.general.statistical.Corrcoef;
import org.jdmp.matrix.calculation.general.statistical.Cov;
import org.jdmp.matrix.calculation.general.statistical.IndexOfMax;
import org.jdmp.matrix.calculation.general.statistical.IndexOfMin;
import org.jdmp.matrix.calculation.general.statistical.Max;
import org.jdmp.matrix.calculation.general.statistical.Mean;
import org.jdmp.matrix.calculation.general.statistical.Min;
import org.jdmp.matrix.calculation.general.statistical.Std;
import org.jdmp.matrix.calculation.general.statistical.Sum;
import org.jdmp.matrix.calculation.general.statistical.Var;
import org.jdmp.matrix.interfaces.Annotation;
import org.jdmp.matrix.interfaces.GUIObject;
import org.jdmp.matrix.interfaces.HasLabel;
import org.jdmp.matrix.interfaces.HasSourceMatrix;
import org.jdmp.matrix.io.Export;
import org.jdmp.matrix.util.JDMPSettings;
import org.jdmp.matrix.util.MathUtil;
import org.jdmp.matrix.util.StringUtil;

public abstract class GenericMatrix<A> extends Matrix {

	private transient GUIObject guiObject = null;

	public abstract A getObject(long... coordinates) throws MatrixException;

	public final GUIObject getGUIObject() {
		if (guiObject == null) {
			try {
				Class<?> c = Class.forName("org.jdmp.core.matrix.MatrixGUIObject");
				Constructor<?> con = c.getConstructor(new Class<?>[] { Matrix.class });
				guiObject = (GUIObject) con.newInstance(new Object[] { this });
			} catch (Exception e) {
				logger.log(Level.WARNING, "cannot create matrix gui object", e);
			}
		}
		return guiObject;
	}

	public final Matrix clone() {
		try {
			return copy(AnnotationTransfer.COPY);
		} catch (MatrixException e) {
			logger.log(Level.WARNING, "Could not clone Matrix, returning original Matrix", e);
			return this;
		}
	}

	public final Matrix select(Ret returnType, long[]... selection) throws MatrixException {
		return new Selection(this, selection).calc(returnType);
	}

	public final Matrix select(Ret returnType, List<? extends Number>... selection)
			throws MatrixException {
		return new Selection(this, selection).calc(returnType);
	}

	public final Matrix selectRows(Ret returnType, long... rows) throws MatrixException {
		return select(returnType, rows, MathUtil.sequenceLong(0, getColumnCount() - 1));
	}

	public final Matrix select(Ret returnType, String selection) throws MatrixException {
		return new Selection(this, selection).calc(returnType);
	}

	public final Matrix selectColumns(Ret returnType, long... columns) throws MatrixException {
		return select(returnType, MathUtil.sequenceLong(0, getRowCount() - 1), columns);
	}

	@SuppressWarnings("unchecked")
	public final Matrix selectRows(Ret returnType, List<? extends Number> rows)
			throws MatrixException {
		return select(returnType, rows, MathUtil.sequenceListLong(0, getColumnCount() - 1));
	}

	@SuppressWarnings("unchecked")
	public final Matrix selectColumns(Ret returnType, List<? extends Number> columns)
			throws MatrixException {
		return select(returnType, MathUtil.sequenceListLong(0, getRowCount() - 1), columns);
	}

	public Matrix imputeKNN(Ret returnType, int dimension) throws MatrixException {
		return new ImputeKNN(dimension, this).calc(returnType);
	}

	public Matrix imputeMean(Ret returnType, int dimension) throws MatrixException {
		return new ImputeMean(dimension, this).calc(returnType);
	}

// public Matrix imputeNeuralNetwork(Ret returnType, int k, double tolerance,
// double learningRate,
// int maxIterations) throws MatrixException {
// return new ImputeNeuralNetwork(this, k, tolerance, learningRate,
// maxIterations)
// .calc(returnType);
// }

	public Matrix imputeRegression(Ret returnType) throws MatrixException {
		return new ImputeRegression(this).calc(returnType);
	}

	public Matrix imputeRegression(Ret returnType, Matrix firstGuess) throws MatrixException {
		return new ImputeRegression(this, firstGuess).calc(returnType);
	}

	public Matrix imputeEM(Ret returnType, int dimension) throws MatrixException {
		return new ImputeEM(dimension, this).calc(returnType);
	}

// public Matrix imputeRescale(Ret returnType, int dimension) throws
// MatrixException {
// return new ImputeRescale(dimension, this).calc(returnType);
// }

	public Matrix imputeZero(Ret returnType) throws MatrixException {
		return new ImputeZero(this).calc(returnType);
	}

	public Matrix indexOfMax(Ret returnType, int dimension) throws MatrixException {
		return new IndexOfMax(dimension, this).calc(returnType);
	}

	public Matrix indexOfMin(Ret returnType, int dimension) throws MatrixException {
		return new IndexOfMin(dimension, this).calc(returnType);
	}

	public Matrix standardize(Ret returnType, int dimension, boolean ignoreNaN)
			throws MatrixException {
		return new Standardize(ignoreNaN, dimension, this).calc(returnType);
	}

	public Matrix inv() throws MatrixException {
		return new Inv(this).calcNew();
	}

	public Matrix princomp() throws MatrixException {
		return new Princomp(this).calcNew();
	}

	public Matrix pinv() throws MatrixException {
		return new Pinv(this).calcNew();
	}

	public Matrix center(Ret returnType, int dimension, boolean ignoreNaN) throws MatrixException {
		return new Center(ignoreNaN, dimension, this).calc(returnType);
	}

	public Matrix copy() throws MatrixException {
		return copy(AnnotationTransfer.LINK);
	}

	public Matrix copy(AnnotationTransfer annotationTransfer) throws MatrixException {
		return Convert.calcNew(annotationTransfer, this);
	}

	public boolean isResizeable() {
		return false;
	}

	public final Matrix convert(EntryType newEntryType) throws MatrixException {
		return Convert.calcNew(newEntryType, AnnotationTransfer.COPY, this);
	}

	public final Matrix convert(EntryType newEntryType, AnnotationTransfer annotationTransfer)
			throws MatrixException {
		return Convert.calcNew(newEntryType, annotationTransfer, this);
	}

	public final Matrix replaceRegex(Ret returnType, Pattern search, String replacement)
			throws MatrixException {
		return new ReplaceRegex(this, search, replacement).calc(returnType);
	}

	public final Matrix replaceRegex(Ret returnType, String search, String replacement)
			throws MatrixException {
		return new ReplaceRegex(this, search, replacement).calc(returnType);
	}

	public Matrix times(double factor) throws MatrixException {
		return Times.calc(false, this, factor);
	}

	public Matrix times(Matrix matrix) throws MatrixException {
		return Times.calc(false, this, matrix);
	}

	public Matrix divide(Matrix m) throws MatrixException {
		return Divide.calc(this, m);
	}

	public Matrix divide(double factor) throws MatrixException {
		return Divide.calc(this, factor);
	}

	public Matrix divide(Ret returnType, boolean ignoreNaN, double factor) throws MatrixException {
		return new Divide(ignoreNaN, this, factor).calc(returnType);
	}

	public Matrix times(Ret returnType, boolean ignoreNaN, double factor) throws MatrixException {
		return new Times(ignoreNaN, this, factor).calc(returnType);
	}

	public Matrix times(Ret returnType, boolean ignoreNaN, Matrix factor) throws MatrixException {
		return new Times(ignoreNaN, this, factor).calc(returnType);
	}

	public Matrix divide(Ret returnType, boolean ignoreNaN, Matrix factor) throws MatrixException {
		return new Divide(ignoreNaN, this, factor).calc(returnType);
	}

	public final Matrix power(Ret returnType, double power) throws MatrixException {
		return new Power(this, power).calc(returnType);
	}

	public final Matrix power(Ret returnType, Matrix power) throws MatrixException {
		return new Power(this, power).calc(returnType);
	}

	public long getValueCount() {
		return Coordinates.product(getSize());
	}

	public final long[] getCoordinatesOfMaximum() throws MatrixException {
		double max = -Double.MAX_VALUE;
		long[] maxc = Coordinates.copyOf(getSize());
		Arrays.fill(maxc, -1);
		for (long[] c : allCoordinates()) {
			double v = getDouble(c);
			if (v > max) {
				max = v;
				maxc = Coordinates.copyOf(c);
			}
		}
		return maxc;
	}

	public final long[] getCoordinatesOfMinimum() throws MatrixException {
		double min = Double.MAX_VALUE;
		long[] minc = Coordinates.copyOf(getSize());
		Arrays.fill(minc, -1);
		for (long[] c : allCoordinates()) {
			double v = getDouble(c);
			if (v < min) {
				min = v;
				minc = Coordinates.copyOf(c);
			}
		}
		return minc;
	}

	public Iterable<long[]> selectedCoordinates(String selection) {
		try {
			return select(Ret.LINK, selection).allCoordinates();
		} catch (MatrixException e) {
			logger.log(Level.WARNING, "could not select coordinates", e);
			return null;
		}
	}

	public Iterable<long[]> selectedCoordinates(long[]... selection) throws MatrixException {
		return select(Ret.LINK, selection).allCoordinates();
	}

	public boolean isTransient() {
		return false;
	}

	public Iterable<long[]> availableCoordinates() {
		return allCoordinates();
	}

	public double[][] toDoubleArray() throws MatrixException {
		int r = (int) getRowCount();
		int c = (int) getColumnCount();
		double[][] values = new double[r][c];
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				values[i][j] = getDouble(i, j);
			}
		}
		return values;
	}

	public Object[][] toObjectArray() throws MatrixException {
		int r = (int) getRowCount();
		int c = (int) getColumnCount();
		Object[][] values = new Object[r][c];
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				values[i][j] = getObject(i, j);
			}
		}
		return values;
	}

	public int[][] toIntArray() throws MatrixException {
		int r = (int) getRowCount();
		int c = (int) getColumnCount();
		int[][] values = new int[r][c];
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				values[i][j] = getInt(i, j);
			}
		}
		return values;
	}

	public long[][] toLongArray() throws MatrixException {
		int r = (int) getRowCount();
		int c = (int) getColumnCount();
		long[][] values = new long[r][c];
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				values[i][j] = getLong(i, j);
			}
		}
		return values;
	}

	public short[][] toShortArray() throws MatrixException {
		int r = (int) getRowCount();
		int c = (int) getColumnCount();
		short[][] values = new short[r][c];
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				values[i][j] = getShort(i, j);
			}
		}
		return values;
	}

	public char[][] toCharArray() throws MatrixException {
		int r = (int) getRowCount();
		int c = (int) getColumnCount();
		char[][] values = new char[r][c];
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				values[i][j] = getChar(i, j);
			}
		}
		return values;
	}

	public String[][] toStringArray() throws MatrixException {
		int r = (int) getRowCount();
		int c = (int) getColumnCount();
		String[][] values = new String[r][c];
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				values[i][j] = getString(i, j);
			}
		}
		return values;
	}

	public byte[][] toByteArray() throws MatrixException {
		int r = (int) getRowCount();
		int c = (int) getColumnCount();
		byte[][] values = new byte[r][c];
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				values[i][j] = getByte(i, j);
			}
		}
		return values;
	}

	public boolean[][] toBooleanArray() throws MatrixException {
		int r = (int) getRowCount();
		int c = (int) getColumnCount();
		boolean[][] values = new boolean[r][c];
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				values[i][j] = getBoolean(i, j);
			}
		}
		return values;
	}

	public float[][] toFloatArray() throws MatrixException {
		int r = (int) getRowCount();
		int c = (int) getColumnCount();
		float[][] values = new float[r][c];
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				values[i][j] = getFloat(i, j);
			}
		}
		return values;
	}

	public Date[][] toDateArray() throws MatrixException {
		int r = (int) getRowCount();
		int c = (int) getColumnCount();
		Date[][] values = new Date[r][c];
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				values[i][j] = getDate(i, j);
			}
		}
		return values;
	}

	public final Matrix sqrt(Ret returnType) throws MatrixException {
		return new Sqrt(this).calc(returnType);
	}

	public final Matrix round(Ret returnType) throws MatrixException {
		return new Round(this).calc(returnType);
	}

	public final Matrix ceil(Ret returnType) throws MatrixException {
		return new Ceil(this).calc(returnType);
	}

	public final Matrix floor(Ret returnType) throws MatrixException {
		return new Floor(this).calc(returnType);
	}

	public final void showGUI() {
		try {
			Class<?> c = Class.forName("org.jdmp.gui.util.FrameManager");
			Method method = c.getMethod("showFrame", new Class[] { GUIObject.class });
			method.invoke(null, new Object[] { getGUIObject() });
		} catch (Exception e) {
			logger.log(Level.WARNING, "cannot show GUI", e);
		}
	}

	public Matrix calcNew(String calculation, Matrix... matrices) throws MatrixException {
		return calcNew(Calc.valueOf(calculation.toUpperCase()), matrices);
	}

	public Matrix calcNew(String calculation, int dimension, Matrix... matrices)
			throws MatrixException {
		return calcNew(Calc.valueOf(calculation.toUpperCase()), dimension, matrices);
	}

	public Matrix calc(String calculation, Ret returnType, Matrix... matrices)
			throws MatrixException {
		return calc(Calc.valueOf(calculation.toUpperCase()), returnType, NONE, matrices);
	}

	public Matrix calc(String calculation, Ret returnType, int dimension, Matrix... matrices)
			throws MatrixException {
		return calc(Calc.valueOf(calculation.toUpperCase()), returnType, dimension, matrices);
	}

	public Matrix calcNew(Calc calculation, Matrix... matrices) throws MatrixException {
		return calc(calculation, Ret.NEW, NONE, matrices);
	}

	public Matrix calcNew(Calc calculation, int dimension, Matrix... matrices)
			throws MatrixException {
		return calc(calculation, Ret.NEW, dimension, matrices);
	}

	public Matrix calc(Calc calculation, Ret returnType, Matrix... matrices) throws MatrixException {
		return calc(calculation, returnType, ALL, matrices);
	}

	public Matrix calc(Calc calculation, Ret returnType, int dimension, Matrix... matrices)
			throws MatrixException {
		return AbstractCalculation.calc(calculation, returnType, dimension, this, matrices);
	}

	public Matrix calcNew(Calculation calculation) throws MatrixException {
		return calc(calculation, Ret.NEW);
	}

	public Matrix calc(Calculation calculation, Ret returnType) throws MatrixException {
		return calculation.calc(returnType);
	}

	public final void notifyGUIObject() {
		if (this instanceof HasSourceMatrix) {
			((HasSourceMatrix) this).getSourceMatrix().notifyGUIObject();
		}
		if (guiObject != null) {
			guiObject.fireValueChanged();
		}
	}

	public Matrix mtimes(Matrix matrix) throws MatrixException {
		return Mtimes.calc(false, this, matrix);
	}

	public final boolean getBoolean(long... coordinates) throws MatrixException {
		return getDouble(coordinates) != 0.0;
	}

	public final void setBoolean(boolean value, long... coordinates) throws MatrixException {
		setDouble(value ? 1.0 : 0.0, coordinates);
	}

	public final int getInt(long... coordinates) throws MatrixException {
		return (int) getDouble(coordinates);
	}

	public final void setInt(int value, long... coordinates) throws MatrixException {
		setDouble(value, coordinates);
	}

	public final byte getByte(long... coordinates) throws MatrixException {
		return (byte) getDouble(coordinates);
	}

	public final void setByte(byte value, long... coordinates) throws MatrixException {
		setDouble(value, coordinates);
	}

	public final char getChar(long... coordinates) throws MatrixException {
		return (char) getDouble(coordinates);
	}

	public final void setChar(char value, long... coordinates) throws MatrixException {
		setDouble(value, coordinates);
	}

	public final float getFloat(long... coordinates) throws MatrixException {
		return (float) getDouble(coordinates);
	}

	public final void setFloat(float value, long... coordinates) throws MatrixException {
		setDouble(value, coordinates);
	}

	public final short getShort(long... coordinates) throws MatrixException {
		return (short) getDouble(coordinates);
	}

	public final void setShort(short value, long... coordinates) throws MatrixException {
		setDouble(value, coordinates);
	}

	public final long getLong(long... coordinates) throws MatrixException {
		return (long) getDouble(coordinates);
	}

	public final void setLong(long value, long... coordinates) throws MatrixException {
		setDouble(value, coordinates);
	}

	public final Date getDate(long... coordinates) throws MatrixException {
		Object o = getObject(coordinates);
		if (o == null) {
			return null;
		}
		if (o instanceof Date) {
			return (Date) o;
		}
		if (o instanceof Long) {
			return new Date((Long) o);
		}
		if (o instanceof String) {
			try {
				return DateFormat.getInstance().parse((String) o);
			} catch (ParseException e) {
			}
		}
		return new Date(getLong(coordinates));
	}

	public final void setDate(Date date, long... coordinates) throws MatrixException {
		setObject(date, coordinates);
	}

	public final Matrix delete(Ret returnType, String selection) throws MatrixException {
		return new Deletion(this, selection).calc(returnType);
	}

	public final Matrix delete(Ret returnType, List<? extends Number>... selection)
			throws MatrixException {
		return new Deletion(this, selection).calc(returnType);
	}

	public final Matrix delete(Ret returnType, long[]... selection) throws MatrixException {
		return new Deletion(this, selection).calc(returnType);
	}

	public final Matrix deleteRows(Ret returnType, long... rows) throws MatrixException {
		return delete(returnType, rows, new long[] {});
	}

	@SuppressWarnings("unchecked")
	public final Matrix deleteRows(Ret returnType, List<? extends Number> rows)
			throws MatrixException {
		return delete(returnType, rows, new ArrayList<Long>());
	}

	@SuppressWarnings("unchecked")
	public final Matrix deleteColumns(Ret returnType, List<? extends Number> columns)
			throws MatrixException {
		return delete(returnType, new ArrayList<Long>(), columns);
	}

	public final Matrix deleteColumns(Ret returnType, long... columns) throws MatrixException {
		return delete(returnType, new long[] {}, columns);
	}

	public Matrix minus(Ret returnType, boolean ignoreNaN, double v) throws MatrixException {
		return calc(new Minus(ignoreNaN, this, v), returnType);
	}

	public Matrix minus(Ret returnType, boolean ignoreNaN, Matrix m) throws MatrixException {
		return calc(new Minus(ignoreNaN, this, m), returnType);
	}

	public Matrix plus(Ret returnType, boolean ignoreNaN, double v) throws MatrixException {
		return calc(new Plus(ignoreNaN, this, v), returnType);
	}

	public Matrix plus(Ret returnType, boolean ignoreNaN, Matrix m) throws MatrixException {
		return calc(new Plus(ignoreNaN, this, m), returnType);
	}

	public Matrix atimes(Ret returnType, boolean ignoreNaN, Matrix matrix) throws MatrixException {

		if (returnType != Ret.NEW) {
			throw new MatrixException("not yet supported");
		}

		int i, j, k, count;
		double sum;

		if (this.getColumnCount() != matrix.getRowCount()) {
			logger.log(Level.WARNING, "matrices have wrong size");
			return null;
		}

		long rowCount = getRowCount();
		long columnCount = getColumnCount();
		long mColumnCount = matrix.getColumnCount();

		Matrix ret = MatrixFactory.zeros(rowCount, mColumnCount);

		if (ignoreNaN) {

			for (i = 0; i < rowCount; i++) {
				for (j = 0; j < mColumnCount; j++) {
					sum = 0.0;
					count = 0;
					for (k = 0; k < columnCount; k++) {
						double v1 = getDouble(i, k);
						double v2 = matrix.getDouble(k, j);
						if (!MathUtil.isNaNOrInfinite(v1) && !MathUtil.isNaNOrInfinite(v2)) {
							sum += v1 * v2;
							count++;
						}
					}
					ret.setDouble(sum / count, i, j);
				}
			}

		} else {

			for (i = 0; i < rowCount; i++) {
				for (j = 0; j < mColumnCount; j++) {
					sum = 0.0;
					count = 0;
					for (k = 0; k < columnCount; k++) {
						double v1 = getDouble(i, k);
						double v2 = matrix.getDouble(k, j);
						sum += v1 * v2;
						count++;
					}
					ret.setDouble(sum / count, i, j);
				}
			}

		}

		return ret;
	}

	public Matrix transpose() throws MatrixException {
		return Transpose.calc(this);
	}

	public Matrix mean(Ret returnType, int dimension, boolean ignoreNaN) throws MatrixException {
		return new Mean(dimension, ignoreNaN, this).calc(returnType);
	}

	public Matrix var(Ret returnType, int dimension, boolean ignoreNaN) throws MatrixException {
		return new Var(dimension, ignoreNaN, this).calc(returnType);
	}

	public Matrix std(Ret returnType, int dimension, boolean ignoreNaN) throws MatrixException {
		return new Std(dimension, ignoreNaN, this).calc(returnType);
	}

	public long getColumnCount() {
		return getSize(COLUMN);
	}

	public long getRowCount() {
		return getSize(ROW);
	}

	public long getZCount() {
		return getSize(Z);
	}

	public final long getSize(int dimension) {
		return getSize()[dimension];
	}

	public final Matrix sum(Ret returnType, int dimension, boolean ignoreNaN)
			throws MatrixException {
		return new Sum(dimension, ignoreNaN, this).calc(returnType);
	}

	public final Matrix sign(Ret returnType) throws MatrixException {
		return new Sign(this).calc(returnType);
	}

	public String toString() {
		StringBuffer s = new StringBuffer();
		try {
			final String EOL = System.getProperty("line.separator");

			long rowCount = getRowCount();
			long columnCount = getColumnCount();
			for (int row = 0; row < rowCount && row < JDMPSettings.getMaxRowsToPrint(); row++) {
				for (int col = 0; col < columnCount && col < JDMPSettings.getMaxColumnsToPrint(); col++) {
					Object o = getObject(row, col);
					String v = StringUtil.format(o);
					while (v.length() < 10) {
						v = " " + v;
					}
					s.append(v);
				}
				s.append(EOL);
			}

			if (rowCount == 0 || columnCount == 0) {
				s.append("[" + rowCount + "x" + columnCount + "]" + EOL);
			}
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not execute toString()", e);
		}
		return s.toString();
	}

	public final int getDimensionCount() {
		return getSize().length;
	}

	public final Matrix ones() throws MatrixException {
		return Ones.calc(this);
	}

	public final Matrix fill(double value) throws MatrixException {
		return Fill.calc(this, value);
	}

	public final Matrix zeros() throws MatrixException {
		return Zeros.calc(this);
	}

	public final Matrix eye() throws MatrixException {
		return Eye.calc(this);
	}

	public Matrix plus(double v) throws MatrixException {
		return Plus.calc(false, this, v);
	}

	public Matrix plus(Matrix m) throws MatrixException {
		return Plus.calc(false, this, m);
	}

	public Matrix minus(double v) throws MatrixException {
		return Minus.calc(false, this, v);
	}

	public Matrix minus(Matrix m) throws MatrixException {
		return Minus.calc(false, this, m);
	}

	public final Matrix link() throws MatrixException {
		return calc(Calc.CLONE, Ret.LINK);
	}

	public void clear() {
		try {
			calc(Calc.ZEROS, Ret.ORIG);
		} catch (MatrixException e) {
			logger.log(Level.WARNING, "could not clear Matrix", e);
		}
	}

	public final Matrix rand() throws MatrixException {
		return Rand.calc(this);
	}

	public final Matrix randn() throws MatrixException {
		return Randn.calc(this);
	}

	public final int compareTo(Matrix m) {
		try {
			return new Double(getEuklideanValue()).compareTo(m.getEuklideanValue());
		} catch (MatrixException e) {
			logger.log(Level.WARNING, "could not compare", e);
			return Integer.MAX_VALUE;
		}
	}

	public int rank() throws MatrixException {
		int rank = 0;

		Matrix[] usv = svd();
		Matrix s = usv[1];

		for (int i = (int) Math.min(s.getSize(ROW), s.getSize(COLUMN)); --i >= 0;) {
			if (Math.abs(s.getDouble(i, i)) > JDMPSettings.getTolerance()) {
				rank++;
			}
		}

		return rank;
	}

	public boolean isSymmetric() {
		// TODO!!!
		return false;
	}

	public boolean isEmpty() throws MatrixException {
		for (long[] c : availableCoordinates()) {
			if (getDouble(c) != 0.0) {
				return false;
			}
		}
		return true;
	}

	public final Matrix abs(Ret returnType) throws MatrixException {
		return new Abs(this).calc(returnType);
	}

	public final Matrix log(Ret returnType) throws MatrixException {
		return new Log(this).calc(returnType);
	}

	public final Matrix log2(Ret returnType) throws MatrixException {
		return new Log2(this).calc(returnType);
	}

	public final Matrix log10(Ret returnType) throws MatrixException {
		return new Log10(this).calc(returnType);
	}

	public final boolean isDiagonal() throws MatrixException {
		if (!isSquare()) {
			return false;
		}
		for (long[] c : allCoordinates()) {
			double v = getDouble(c);
			if (v != 0.0) {
				for (int i = 1; i < c.length; i++) {
					if (c[i - 1] != c[i]) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public final boolean isSquare() {
		return getSize().length == 2 && getColumnCount() == getRowCount();
	}

	public double euklideanDistanceTo(Matrix m, boolean ignoreNaN) throws MatrixException {
		return minkowskiDistanceTo(m, 2, ignoreNaN);
	}

	public double manhattenDistanceTo(Matrix m, boolean ignoreNaN) throws MatrixException {
		return minkowskiDistanceTo(m, 1, ignoreNaN);
	}

	public double minkowskiDistanceTo(Matrix m, double p, boolean ignoreNaN) throws MatrixException {
		double sum = 0.0;
		if (ignoreNaN) {
			for (long[] c : allCoordinates()) {
				sum += MathUtil.ignoreNaN(Math.pow(Math.abs((getDouble(c)) - m.getDouble(c)), p));
			}
		} else {
			for (long[] c : allCoordinates()) {
				sum += Math.pow(Math.abs((getDouble(c)) - m.getDouble(c)), p);
			}
		}
		return Math.pow(sum, 1 / p);
	}

	public double chebyshevDistanceTo(Matrix m, boolean ignoreNaN) throws MatrixException {
		double max = 0.0;
		if (ignoreNaN) {
			for (long[] c : allCoordinates()) {
				double v = MathUtil.ignoreNaN(Math.abs((getDouble(c) - m.getDouble(c))));
				max = v > max ? v : max;
			}
		} else {
			for (long[] c : allCoordinates()) {
				double v = Math.abs((getDouble(c) - m.getDouble(c)));
				max = v > max ? v : max;
			}
		}
		return max;
	}

	public Matrix min(Ret returnType, int dimension) throws MatrixException {
		return new Min(dimension, this).calc(returnType);
	}

	public Matrix max(Ret returnType, int dimension) throws MatrixException {
		return new Max(dimension, this).calc(returnType);
	}

	public final Matrix addMissing(Ret returnType, int dimension, double... percentMissing)
			throws MatrixException {
		return new AddMissing(dimension, this, percentMissing).calc(returnType);
	}

	public Matrix countMissing(Ret returnType, int dimension) throws MatrixException {
		return new CountMissing(dimension, this).calc(returnType);
	}

	public final boolean isScalar() {
		return getColumnCount() == 1 && getRowCount() == 1;
	}

	public final boolean isRowVector() {
		return getColumnCount() == 1 && getRowCount() != 1;
	}

	public final boolean isColumnVector() {
		return getColumnCount() != 1 && getRowCount() == 1;
	}

	public final boolean isMultidimensionalMatrix() {
		return getColumnCount() != 1 && getRowCount() != 1;
	}

	public Matrix sinh(Ret returnType) throws MatrixException {
		return new Sinh(this).calc(returnType);
	}

	public Matrix cosh(Ret returnType) throws MatrixException {
		return new Cosh(this).calc(returnType);
	}

	public Matrix tanh(Ret returnType) throws MatrixException {
		return new Tanh(this).calc(returnType);
	}

	public Matrix sin(Ret returnType) throws MatrixException {
		return new Sin(this).calc(returnType);
	}

	public Matrix cos(Ret returnType) throws MatrixException {
		return new Cos(this).calc(returnType);
	}

	public Matrix tan(Ret returnType) throws MatrixException {
		return new Tan(this).calc(returnType);
	}

	public Matrix cov(Ret returnType, boolean ignoreNaN) throws MatrixException {
		return new Cov(ignoreNaN, this).calc(returnType);
	}

	public Matrix corrcoef(Ret returnType, boolean ignoreNaN) throws MatrixException {
		return new Corrcoef(ignoreNaN, this).calc(returnType);
	}

	public double trace() throws MatrixException {
		double sum = 0.0;
		for (long i = Math.min(getRowCount(), getColumnCount()); --i >= 0;) {
			sum += getDouble(i, i);
		}
		return sum;
	}

	public final void exportToFile(File file, Object... parameters) throws MatrixException {
		Export.save(file, this, parameters);
	}

	public final void exportToFile(String file, Object... parameters) throws MatrixException {
		Export.save(file, this, parameters);
	}

	public final void exportToFile(Format format, File file, Object... parameters)
			throws MatrixException {
		Export.save(format, file, this, parameters);
	}

	public final void exportToFile(Format format, String file, Object... parameters)
			throws MatrixException {
		Export.save(format, file, this, parameters);
	}

	public final void setLabel(String label) {
		setMatrixAnnotation(label);
	}

	public final String getLabel() {
		Object o = getMatrixAnnotation();
		if (o == null) {
			return null;
		}
		if (o instanceof String) {
			return (String) o;
		}
		if (o instanceof HasLabel) {
			return ((HasLabel) o).getLabel();
		}
		return o.toString();
	}

	public void setString(String string, long... coordinates) throws MatrixException {
		setObject(string, coordinates);
	}

	public boolean isReadOnly() {
		return false;
	}

	public String getString(long... coordinates) throws MatrixException {
		Object o = getObject(coordinates);
		if (o == null) {
			return null;
		}
		if (o instanceof String) {
			return (String) o;
		}
		if (o instanceof HasLabel) {
			return ((HasLabel) o).getLabel();
		}
		return o.toString();
	}

	public final double getMaxValue() throws MatrixException {
		return Max.calc(this);
	}

	public final double getMinValue() throws MatrixException {
		return Min.calc(this);
	}

	public final double getMeanValue() throws MatrixException {
		return Mean.calc(this);
	}
	
	public final double getStdValue() throws MatrixException {
		return std(Ret.NEW,Matrix.ALL,true).getEuklideanValue();
	}

	public void setMatrixAnnotation(Object o) {
	}

	public Object getMatrixAnnotation() {
		return null;
	}

	public Object getAxisAnnotation(int axis, int positionOnAxis) {
		return null;
	}

	public final Object getAxisAnnotation(int axis, int positionOnAxis, Object key) {
		return null;
	}

	public void setAxisAnnotation(int axis, int positionOnAxis, Object value) {
	}

	public Object getAxisAnnotation(int axis) {
		return null;
	}

	public void setAxisAnnotation(int axis, Object value) {
	}

	public Annotation getAnnotation() {
		return null;
	}

	public void setAnnotation(Annotation axisAnnotation) {
	}

	public final boolean equalsAnnotation(Object o) {
		if (this == o) {
			return true;
		}
		if (o instanceof Matrix) {
			Matrix m = (Matrix) o;
			Annotation a1 = getAnnotation();
			Annotation a2 = m.getAnnotation();
			if (a1 != null) {
				if (!a1.equals(a2)) {
					return false;
				}
			} else if (a2 != null) {
				return false;
			}
		}
		return true;
	}

	public final boolean equals(Object o) {
		return equalsContent(o) && equalsAnnotation(o);
	}

	public final boolean equalsContent(Object o) {

		try {

			if (this == o) {
				return true;
			}
			if (o instanceof Matrix) {
				Matrix m = (Matrix) o;
				if (!Coordinates.equals(getSize(), m.getSize())) {
					return false;
				}
				if (isSparse() && m.isSparse()) {
					for (long[] c : availableCoordinates()) {
						Object o1 = getObject(c);
						Object o2 = m.getObject(c);
						if ((o1 == null && o2 != null) || (o1 != null && o2 == null)) {
							return false;
						}
						if (o1 != null && o2 != null) {
							if (!o1.equals(o2)) {
								return false;
							}
						} else {
							return false;
						}
					}
					for (long[] c : m.availableCoordinates()) {
						Object o1 = getObject(c);
						Object o2 = m.getObject(c);
						if ((o1 == null && o2 != null) || (o1 != null && o2 == null)) {
							return false;
						}
						if (o1 != null && o2 != null) {
							if (!o1.equals(o2)) {
								return false;
							}
						} else {
							return false;
						}
					}
				} else {
					for (long[] c : allCoordinates()) {
						Object o1 = getObject(c);
						Object o2 = m.getObject(c);
						if ((o1 == null && o2 != null) || (o1 != null && o2 == null)) {
							return false;
						}
						if (o1 != null && o2 != null) {
							if (!o1.equals(o2)) {
								return false;
							}
						} else if (o1 == null && o2 == null) {
						} else {
							return false;
						}
					}
				}
				return true;
			}
			return false;

		} catch (Exception e) {
			logger.log(Level.WARNING, "could not compare", e);
			return false;
		}

	}

}
