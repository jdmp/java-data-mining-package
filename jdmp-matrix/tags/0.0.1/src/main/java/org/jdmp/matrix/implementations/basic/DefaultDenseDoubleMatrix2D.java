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

package org.jdmp.matrix.implementations.basic;

import java.util.logging.Level;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.coordinates.Coordinates;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.stubs.AbstractDenseDoubleMatrix2D;

public class DefaultDenseDoubleMatrix2D extends AbstractDenseDoubleMatrix2D {
	private static final long serialVersionUID = 3132491298449205914L;

	private double[][] values = null;

	public DefaultDenseDoubleMatrix2D(Matrix m) throws MatrixException {
		if (m instanceof DefaultDenseDoubleMatrix2D) {
			double[][] v = ((DefaultDenseDoubleMatrix2D) m).values;
			this.values = new double[v.length][v[0].length];
			for (int r = v.length; --r >= 0;) {
				for (int c = v[0].length; --c >= 0;) {
					values[r][c] = v[r][c];
				}
			}
		} else {
			values = new double[(int) m.getRowCount()][(int) m.getColumnCount()];
			for (long[] c : m.allCoordinates()) {
				setDouble(m.getDouble(c), c);
			}
		}
	}

	public DefaultDenseDoubleMatrix2D(double[]... v) {
		this.values = v;
	}

	public DefaultDenseDoubleMatrix2D(long... size) {
		values = new double[(int) size[ROW]][(int) size[COLUMN]];
	}

	public DefaultDenseDoubleMatrix2D(double[] v) {
		this.values = new double[v.length][1];
		for (int r = v.length; --r >= 0;) {
			values[r][0] = v[r];
		}
	}

	public long[] getSize() {
		return new long[] { values.length, values[0].length };
	}

	@Override
	public long getRowCount() {
		return values.length;
	}

	@Override
	public long getColumnCount() {
		return values[0].length;
	}

	public double getDouble(long... coordinates) {
		return values[(int) coordinates[ROW]][(int) coordinates[COLUMN]];
	}

	public void setDouble(double value, long... coordinates) {
		values[(int) coordinates[ROW]][(int) coordinates[COLUMN]] = value;
	}

	public final Matrix transpose() {
		double[][] result = new double[values[0].length][values.length];
		for (int r = result.length; --r >= 0;) {
			for (int c = result[0].length; --c >= 0;) {
				result[r][c] = values[c][r];
			}
		}
		return new DefaultDenseDoubleMatrix2D(result);
	}

	public final Matrix plus(double v) {
		double[][] result = new double[values.length][values[0].length];
		for (int r = result.length; --r >= 0;) {
			for (int c = result[0].length; --c >= 0;) {
				result[r][c] = values[r][c] + v;
			}
		}
		return new DefaultDenseDoubleMatrix2D(result);
	}

	public final Matrix minus(double v) {
		double[][] result = new double[values.length][values[0].length];
		for (int r = result.length; --r >= 0;) {
			for (int c = result[0].length; --c >= 0;) {
				result[r][c] = values[r][c] - v;
			}
		}
		return new DefaultDenseDoubleMatrix2D(result);
	}

	public final Matrix times(double v) {
		double[][] result = new double[values.length][values[0].length];
		for (int r = result.length; --r >= 0;) {
			for (int c = result[0].length; --c >= 0;) {
				result[r][c] = values[r][c] * v;
			}
		}
		return new DefaultDenseDoubleMatrix2D(result);
	}

	public final Matrix divide(double v) {
		double[][] result = new double[values.length][values[0].length];
		for (int r = result.length; --r >= 0;) {
			for (int c = result[0].length; --c >= 0;) {
				result[r][c] = values[r][c] / v;
			}
		}
		return new DefaultDenseDoubleMatrix2D(result);
	}

	public final Matrix plus(Matrix m2) throws MatrixException {
		double[][] result = new double[values.length][values[0].length];
		for (int r = result.length; --r >= 0;) {
			for (int c = result[0].length; --c >= 0;) {
				result[r][c] = values[r][c] + m2.getDouble(r, c);
			}
		}
		return new DefaultDenseDoubleMatrix2D(result);
	}

	public final Matrix minus(Matrix m2) throws MatrixException {
		double[][] result = new double[values.length][values[0].length];
		for (int r = result.length; --r >= 0;) {
			for (int c = result[0].length; --c >= 0;) {
				result[r][c] = values[r][c] - m2.getDouble(r, c);
			}
		}
		return new DefaultDenseDoubleMatrix2D(result);
	}

	public final Matrix times(Matrix m2) throws MatrixException {
		double[][] result = new double[values.length][values[0].length];
		for (int r = result.length; --r >= 0;) {
			for (int c = result[0].length; --c >= 0;) {
				result[r][c] = values[r][c] * m2.getDouble(r, c);
			}
		}
		return new DefaultDenseDoubleMatrix2D(result);
	}

	public final Matrix divide(Matrix m2) throws MatrixException {
		double[][] result = new double[values.length][values[0].length];
		for (int r = result.length; --r >= 0;) {
			for (int c = result[0].length; --c >= 0;) {
				result[r][c] = values[r][c] / m2.getDouble(r, c);
			}
		}
		return new DefaultDenseDoubleMatrix2D(result);
	}

	public Matrix mtimes(Matrix matrix) throws MatrixException {
		if (values[0].length != matrix.getRowCount()) {
			logger.log(Level.WARNING, "matrices have wrong size: "
					+ Coordinates.toString(getSize()) + " and "
					+ Coordinates.toString(matrix.getSize()), new Exception());
			return null;
		}

		int i, j, k;
		double sum;
		double v1, v2;
		double[][] ret = new double[(int) values.length][(int) matrix.getColumnCount()];

		for (i = values.length; --i >= 0;) {
			for (j = ret[0].length; --j >= 0;) {
				sum = 0.0;
				for (k = values[0].length; --k >= 0;) {
					sum += values[i][k] * matrix.getDouble(k, j);
				}
				ret[i][j] = sum;
			}
		}

		return new DefaultDenseDoubleMatrix2D(ret);
	}

	public boolean containsNaN() {
		for (int r = values.length; --r >= 0;) {
			for (int c = values[0].length; --c >= 0;) {
				if (Double.isNaN(values[r][c])) {
					return true;
				}
			}
		}
		return false;
	}
}