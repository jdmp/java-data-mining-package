package org.jdmp.matrix.stubs;

import org.jdmp.matrix.AbstractGenericMatrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.ObjectMatrix;
import org.jdmp.matrix.util.MathUtil;

public abstract class AbstractObjectMatrix extends AbstractGenericMatrix<Object> implements ObjectMatrix {

	public final double getDouble(long... coordinates) throws MatrixException {
		return MathUtil.getDouble(getObject(coordinates));
	}

	public final void setDouble(double v, long... coordinates) throws MatrixException {
		setObject(v, coordinates);
	}

	public final EntryType getEntryType() {
		return EntryType.OBJECT;
	}

}
