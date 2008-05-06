package org.jdmp.matrix;

public class MatrixException extends RuntimeException {
	private static final long serialVersionUID = -2291815899552676592L;

	public MatrixException(String message) {
		super(message);
	}

	public MatrixException(String message, Throwable cause) {
		super(message, cause);
	}

	public MatrixException(Throwable cause) {
		super(cause);
	}

}
