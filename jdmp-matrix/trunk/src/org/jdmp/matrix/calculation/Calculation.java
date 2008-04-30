package org.jdmp.matrix.calculation;

import java.io.Serializable;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;

public interface Calculation extends Serializable {

	public static final int ALL = Matrix.ALL;

	public static final int NONE = Matrix.NONE;

	public static final int ROW = Matrix.ROW;

	public static final int COLUMN = Matrix.COLUMN;

	public enum Ret {
		NEW, LINK, ORIG
	};

	public enum Calc {
		CLONE, ZEROS, ONES, EYE, FILL, ROUND, CEIL, FLOOR, RAND, RANDN, ABS, LOG, MAX, MIN, MINUS, PLUS, SUM, TRANSPOSE, TIMES, MTIMES, DIVIDE, POWER, SIGN, SQRT, SIN, COS, TAN, SINH, COSH, TANH
	};

	public Matrix calc(Ret returnType) throws MatrixException;

	public Matrix calcNew() throws MatrixException;

	public Matrix calcLink() throws MatrixException;

	public Matrix calcOrig() throws MatrixException;

}
