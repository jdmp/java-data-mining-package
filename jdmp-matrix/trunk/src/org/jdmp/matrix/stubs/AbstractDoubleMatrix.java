package org.jdmp.matrix.stubs;

import org.jdmp.matrix.GenericMatrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.util.MathUtil;

public abstract class AbstractDoubleMatrix extends GenericMatrix<Double> {

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
