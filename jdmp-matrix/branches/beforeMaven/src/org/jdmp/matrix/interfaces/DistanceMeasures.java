package org.jdmp.matrix.interfaces;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;

public interface DistanceMeasures {

	public double euklideanDistanceTo(Matrix m, boolean ignoreNaN) throws MatrixException;

	public double manhattenDistanceTo(Matrix m, boolean ignoreNaN) throws MatrixException;

	public double minkowskiDistanceTo(Matrix m, double p, boolean ignoreNaN) throws MatrixException;

	public double chebyshevDistanceTo(Matrix m, boolean ignoreNaN) throws MatrixException;

}
