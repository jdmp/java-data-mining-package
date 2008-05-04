package org.jdmp.matrix.stubs;

import org.jdmp.matrix.DoubleMatrix;
import org.jdmp.matrix.AbstractGenericMatrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.util.MathUtil;

public abstract class AbstractDoubleMatrix extends AbstractGenericMatrix<Double> implements DoubleMatrix {

	public final Double getObject(long... coordinates) throws MatrixException {
		return getDouble(coordinates);
	}

	public final void setObject(Object o, long... coordinates) throws MatrixException {
		setDouble(MathUtil.getDouble(o), coordinates);
	}

	public final EntryType getEntryType() {
		return EntryType.DOUBLE;
	}

}
