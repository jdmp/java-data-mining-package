package org.jdmp.matrix.stubs;

import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.StringMatrix;
import org.jdmp.matrix.util.StringUtil;

public abstract class AbstractStringMatrix extends AbstractGenericMatrix<String> implements
		StringMatrix {

	public final String getObject(long... coordinates) throws MatrixException {
		return getString(coordinates);
	}

	public final void setObject(Object o, long... coordinates) throws MatrixException {
		setString(StringUtil.convert(o), coordinates);
	}

	public final double getDouble(long... coordinates) {
		try {
			return Double.parseDouble(getString(coordinates));
		} catch (Exception e) {
			return Double.NaN;
		}
	}

	public final void setDouble(double value, long... coordinates) throws MatrixException {
		setString("" + value, coordinates);
	}

	public final EntryType getEntryType() {
		return EntryType.STRING;
	}

}
