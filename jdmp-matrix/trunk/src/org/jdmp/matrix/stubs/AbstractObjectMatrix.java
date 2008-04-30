package org.jdmp.matrix.stubs;

import org.jdmp.matrix.GenericMatrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.util.MathUtil;

public abstract class AbstractObjectMatrix<A> extends GenericMatrix<A> {

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
